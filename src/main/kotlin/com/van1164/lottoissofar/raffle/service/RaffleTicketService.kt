package com.van1164.lottoissofar.raffle.service

import com.van1164.lottoissofar.common.discord.DiscordService
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.dto.sms.SmsMessageDto
import com.van1164.lottoissofar.common.exception.ErrorCode.*
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.email.EmailService
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryRepository
import com.van1164.lottoissofar.raffle.exception.RaffleExceptions
import com.van1164.lottoissofar.raffle.repository.RaffleRepository
import com.van1164.lottoissofar.sms.SmsService
import com.van1164.lottoissofar.ticket.service.TicketService
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.winner_history.service.WinnerHistoryService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.redisson.api.RedissonClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime


@Service
class RaffleTicketService(
    private val raffleRepository: RaffleRepository,
    private val userRepository: UserJpaRepository,
    private val itemRepository: ItemJpaRepository,
    private val purchaseHistoryRepository: PurchaseHistoryRepository,
    private val redissonClient: RedissonClient,
    private val emailService: EmailService,
    private val discordService: DiscordService,
    private val smsService: SmsService,
    private val tickerService: TicketService,
    private val ticketService: TicketService,
    private val winnerHistoryService: WinnerHistoryService,
) {
    @Transactional
    fun purchaseWithTicket(
        raffleId: Long,
        ticketCount: Int,
        userId: Long
    ): ResponseEntity<MutableList<PurchaseHistory>> {
        val user = userRepository.findById(userId).orElseThrow {
            GlobalExceptions.NotFoundException(
                USER_NOT_FOUND
            )
        }
        if (user.tickets < ticketCount) {
            throw RaffleExceptions.ExceedTickets()
        }

        val raffle = raffleRepository.findById(raffleId)
            .orElseThrow { GlobalExceptions.NotFoundException(RAFFLE_NOT_FOUND) }

        if (raffle.status != RaffleStatus.ACTIVE) {
            throw RaffleExceptions.AlreadyFinishedException(RAFFLE_ALREADY_INACTIVE)
        }

        if (raffle.currentCount >= raffle.totalCount) {
            throw RaffleExceptions.AlreadyFinishedException(RAFFLE_MAX_CAPACITY_REACHED)
        }
        if (raffle.totalCount < raffle.currentCount + ticketCount) {
            throw RaffleExceptions.TotalTicketExceed()
        }

//        if (purchaseHistoryJpaRepository.existsDistinctByUserAndRaffle(user, raffle)) {
//            throw RaffleExceptions.AlreadyPurchasedException("이미 구매한 Raffle입니다.")
//        }

        raffle.currentCount += ticketCount
        val history = createPurchaseHistoryWithTickets(raffle, user, ticketCount)

        if (raffle.currentCount == raffle.totalCount) {
            completeRaffle(raffle)
        }

        user.tickets -= ticketCount
        ticketService.saveTicket(TicketHistory(userId = userId, ticketCount = user.tickets))
        return ResponseEntity.ok().body(history)
    }

    private fun createPurchaseHistoryWithTickets(
        raffle: Raffle,
        user: User,
        ticketCount: Int
    ): MutableList<PurchaseHistory> {
        val historyList = mutableListOf<PurchaseHistory>()
        repeat(ticketCount) {
            val history = PurchaseHistory(user, raffle)
            raffle.purchaseHistoryList.add(history)
            user.purchaseHistoryList.add(history)
            purchaseHistoryRepository.save(history)
            historyList.add(history)
        }
        return historyList
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun completeRaffle(raffle: Raffle) {
        val winner = raffle.purchaseHistoryList.random().user
        raffle.winner = winner
        raffle.status = RaffleStatus.COMPLETED
        raffle.completedDate = LocalDateTime.now()
        winnerHistoryService.saveWinnerHistory(WinnerHistory(winner.userId, raffle.id))
        GlobalScope.launch {
            notifyWinner(raffle, winner)
        }

        createNewRaffle(raffle)


    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun createNewRaffle(raffle: Raffle) {
        if (!raffle.item.possibleRaffle) {
            return
        }
        val newRaffle = Raffle(
            totalCount = raffle.totalCount,
            ticketPrice = raffle.ticketPrice,
            currentCount = 0,
            status = RaffleStatus.ACTIVE,
            item = raffle.item,
            isFree = raffle.isFree
        )

        raffleRepository.save(newRaffle).run {
            GlobalScope.launch {
                notifyNewRaffle(this@run)
            }
        }
    }

    fun notifyWinner(raffle: Raffle, winner: User) {
        try {
            emailService.sendEmail("Raffle 당첨을 축하드립니다.", raffle, winner)
        } catch (e: Exception) {
            println("MAIL ERROR")
            println("사용자 ID: " + winner.userId + "\n raffle ID: " + raffle.id + "| 메일 전송실패")
            discordService.sendMessage("사용자 ID: " + winner.userId + "\n raffle ID: " + raffle.id + "| 메일 전송실패")
        }

        try {
            val phoneNumber = winner.phoneNumber ?: throw RuntimeException()
            println("전화번호 : $phoneNumber")
            val smsMessageDto = SmsMessageDto(
                to = phoneNumber,
                message = "[올유레플] ${raffle.item.name}에 당첨되었습니다.\n배송 주소변경을 원하시면 금일까지 사이트에서 수정 부탁드립니다."
            )
            val response = smsService.sendOne(smsMessageDto)
            println("ZZZZZZZZZZZZZZZZZZZZZZZZ")
            println(response)
        } catch (e: Exception) {
            println("SMS ERROR")
            println("사용자 ID: " + winner.userId + "\n raffle ID: " + raffle.id + "| 문자 전송실패")
            discordService.sendMessage("사용자 ID: " + winner.userId + "\n사용자 전화번호: ${winner.phoneNumber}\n raffle ID: " + raffle.id + "| 문자 전송실패")
        }


        try {
            discordService.sendMessage(raffle.id.toString() + "번 래플 당첨자 발생." + "(${raffle.item.name})\n 당첨자 아이디 : ${winner.userId}")
        } catch (e: Exception) {
            println("DISCORD ERROR")
            println("사용자 ID: " + winner.userId + "\n raffle ID: " + raffle.id + "| 디스코드 전송 실패")
        }


        // 알림 보내기 로직 구현
    }

    fun notifyNewRaffle(raffle: Raffle) {
        try {
            discordService.sendMessage(raffle.id.toString() + "번 래플 새로 시작됨." + "(${raffle.item.name})")
        } catch (e: Exception) {
            println("DISCORD ERROR")
            println("raffle ID: " + raffle.id + "| 새 래플 시작 디스코드 전송 실패")
        }

        // 새로운 Raffle 시작 알림 로직 구현
    }
}