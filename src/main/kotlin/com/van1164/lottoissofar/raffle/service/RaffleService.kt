package com.van1164.lottoissofar.raffle.service

import com.van1164.lottoissofar.common.discord.DiscordService
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.dto.sms.SmsMessageDto
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryRepository
import com.van1164.lottoissofar.email.EmailService
import com.van1164.lottoissofar.raffle.exception.RaffleExceptions
import com.van1164.lottoissofar.raffle.repository.RaffleRepository
import com.van1164.lottoissofar.sms.SmsService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
@EnableAsync
class RaffleService(
    private val raffleRepository: RaffleRepository,
    private val purchaseHistoryRepository: PurchaseHistoryRepository,
    private val redissonClient: RedissonClient,
    private val emailService: EmailService,
    private val discordService: DiscordService,
    private val smsService: SmsService,
    private val raffleTicketService: RaffleTicketService
) {

    //    @Transactional
    fun purchaseRaffle(raffleId: Long, user: User): ResponseEntity<PurchaseHistory> {
        val raffleLock: RLock = redissonClient.getLock("raffleLock:$raffleId")
        try {
            if (raffleLock.tryLock(10, TimeUnit.SECONDS)) {
                return purchase(raffleId, user)
            } else {
                throw GlobalExceptions.InternalErrorException("Raffle 결제 과정에서 시간초과가 발생했습니다.")
            }
        } finally {
            if (raffleLock.isHeldByCurrentThread) {
                raffleLock.unlock()
            }
        }
    }

    @Transactional
    fun purchase(
        raffleId: Long,
        user: User
    ): ResponseEntity<PurchaseHistory> {
        val raffle = raffleRepository.findById(raffleId)
            .orElseThrow { GlobalExceptions.NotFoundException("Raffle을 찾을 수 없습니다.") }

        if (raffle.status != RaffleStatus.ACTIVE) {
            throw RaffleExceptions.AlreadyFinishedException("이미 완료된 Raffle입니다. 새로운 Raffle에 참가해주세요.")
        }

        if (raffle.currentCount >= raffle.totalCount) {
            throw RaffleExceptions.AlreadyFinishedException("이미 완료된 Raffle입니다. 새로운 Raffle에 참가해주세요.")
        }

//        if (purchaseHistoryJpaRepository.existsDistinctByUserAndRaffle(user, raffle)) {
//            throw RaffleExceptions.AlreadyPurchasedException("이미 구매한 Raffle입니다.")
//        }

        raffle.currentCount += 1
        val history = createPurchaseHistory(raffle, user)

        if (raffle.currentCount == raffle.totalCount) {
            completeRaffle(raffle)
        }
        return ResponseEntity.ok().body(history)
    }


    fun purchaseRaffleOneTicket(raffleId: Long,userId: Long): ResponseEntity<MutableList<PurchaseHistory>> {
        return purchaseRaffleWithTicket(raffleId,1,userId)
    }
    fun purchaseRaffleWithTicket(raffleId: Long, ticketCount: Int, userId : Long): ResponseEntity<MutableList<PurchaseHistory>> {
        val raffleLock: RLock = redissonClient.getLock("raffleLock:$raffleId")
        val userLock: RLock = redissonClient.getLock("userLock:${userId}")
        try {
            val locked: Boolean = userLock.tryLock(10, TimeUnit.SECONDS) &&
                    raffleLock.tryLock(10, TimeUnit.SECONDS)
            if (locked) {
                return raffleTicketService.purchaseWithTicket(raffleId, ticketCount, userId)
            } else {
                throw GlobalExceptions.InternalErrorException("Raffle 결제 과정에서 시간초과가 발생했습니다.")
            }
        } finally {
            if (raffleLock.isHeldByCurrentThread) {
                raffleLock.unlock()
            }
            if (userLock.isHeldByCurrentThread){
                userLock.unlock()
            }
        }
    }

    private fun createPurchaseHistory(raffle: Raffle, user: User): PurchaseHistory {
        val history = PurchaseHistory(user, raffle)
        raffle.purchaseHistoryList.add(history)
        user.purchaseHistoryList.add(history)
        purchaseHistoryRepository.save(history)
        return history
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun completeRaffle(raffle: Raffle) {
        val winner = raffle.purchaseHistoryList.random().user
        raffle.winner = winner
        raffle.status = RaffleStatus.COMPLETED
        raffle.completedDate = LocalDateTime.now()
//        raffleRepository.save(raffle)
        GlobalScope.launch {
            notifyWinner(raffle, winner)
        }

        createNewRaffle(raffle)


//        notifyNewRaffle(raffle.item)
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

    fun createNewRaffle(item: Item, totalCount: Int? = null, ticketPrice: Int? = null): Raffle {
        val newRaffle = Raffle(
            totalCount = totalCount ?: item.defaultTotalCount,
            ticketPrice = ticketPrice ?: 1000,
            currentCount = 0,
            status = RaffleStatus.ACTIVE,
            item = item
        )
        raffleRepository.save(newRaffle)

        return newRaffle
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

    fun getActive(): List<Raffle> {
        return raffleRepository.findAllByStatusIsACTIVE()
    }

    fun getActiveFreeRaffle(): List<Raffle> {
        return raffleRepository.findAllByStatusIsACTIVEAndFree()
    }

    fun getActiveNotFreeRaffle(): List<Raffle> {
        return raffleRepository.findAllByStatusIsACTIVEAndNotFree()
    }

    fun getAll(): List<Raffle> {
        return raffleRepository.findAll()
    }

    fun getDetailFree(raffleId: Long): ResponseEntity<Raffle> {
        return raffleRepository.findByStatusIsACTIVEAndFree(raffleId)?.let {
            ResponseEntity.ok(it)
        } ?: run {
            throw GlobalExceptions.NotFoundException("이미 마감된 래플입니다.")
        }
    }

    fun getDetailNotFree(raffleId: Long): ResponseEntity<Raffle> {
        return raffleRepository.findByStatusIsACTIVEAndNotFree(raffleId)?.let {
            ResponseEntity.ok(it)
        } ?: run {
            throw GlobalExceptions.NotFoundException("이미 마감된 래플입니다.")
        }
    }

    fun getActivePopular(): List<Raffle> {
        return raffleRepository.findAllByStatusIsACTIVEPopular()
    }
}