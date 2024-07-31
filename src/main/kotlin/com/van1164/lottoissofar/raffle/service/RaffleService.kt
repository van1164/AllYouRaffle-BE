package com.van1164.lottoissofar.raffle.service

import com.van1164.lottoissofar.common.domain.Item
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.raffle.exception.RaffleExceptions
import com.van1164.lottoissofar.raffle.repository.RaffleJpaRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

@Service
class RaffleService(
    private val raffleRepository: RaffleJpaRepository,
    private val userRepository: UserJpaRepository,
    private val itemRepository: ItemJpaRepository,
    private val redissonClient: RedissonClient
) {
    @Transactional
    fun purchaseRaffle(raffleId: Long, userId: Long) {
        val raffleLock: RLock = redissonClient.getLock("raffleLock:$raffleId")
        try {
            if (raffleLock.tryLock(10, TimeUnit.SECONDS)) {
                val raffle = raffleRepository.findById(raffleId)
                    .orElseThrow { GlobalExceptions.NotFoundException("Raffle을 찾을 수 없습니다.") }

                if (raffle.status != RaffleStatus.ACTIVE) {
                    throw RaffleExceptions.AlreadyFinishedException("이미 완료된 Raffle입니다. 새로운 Raffle에 참가해주세요.")
                }

                if (raffle.currentCount >= raffle.totalCount) {
                    throw RaffleExceptions.AlreadyFinishedException("이미 완료된 Raffle입니다. 새로운 Raffle에 참가해주세요.")
                }

                val user = userRepository.findById(userId).orElseThrow { GlobalExceptions.NotFoundException("사용자를 찾을 수 없습니다.") }
                raffle.purchaseUsers.add(user)
                user.purchaseRaffles.add(raffle)
                raffle.currentCount += 1

                if (raffle.currentCount == raffle.totalCount) {
                    completeRaffle(raffle)
                }

//                raffleRepository.save(raffle)
            } else {
                throw GlobalExceptions.InternalErrorException("Raffle 결제 과정에서 오류가 발생했습니다.")
            }
        } finally {
            if (raffleLock.isHeldByCurrentThread) {
                raffleLock.unlock()
            }
        }
    }

    private fun completeRaffle(raffle: Raffle) {
        val winner = raffle.purchaseUsers.random()
        raffle.winner = winner
        raffle.status = RaffleStatus.COMPLETED
        raffle.completedDate = LocalDateTime.now()
//        raffleRepository.save(raffle)
        notifyWinner(winner)
        createNewRaffle(raffle.item)
        notifyNewRaffle(raffle.item)
    }

    private fun createNewRaffle(item: Item) {
        val newRaffle = Raffle(
            totalCount = item.defaultTotalCount,
            currentCount = 0,
            status = RaffleStatus.ACTIVE,
            item = item
        )
        raffleRepository.save(newRaffle)
    }

    private fun notifyWinner(winner: User) {
        println("알림 전송")
        // 알림 보내기 로직 구현
    }

    private fun notifyNewRaffle(item: Item) {
        println("서버로 새 raffle 생성 알림")
        // 새로운 Raffle 시작 알림 로직 구현
    }
}