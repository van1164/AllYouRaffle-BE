package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.user.FcmTokenDto
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.common.exception.ErrorCode.NOT_FOUND
import com.van1164.lottoissofar.common.exception.ErrorCode.USER_TICKET_LOCK_TIMEOUT
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.ticket.service.TicketService
import com.van1164.lottoissofar.user.exception.AlreadySavedPhoneNumber
import com.van1164.lottoissofar.user.repository.DeleteUserRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val deletedUserRepository: DeleteUserRepository,
    private val jwtUtil: JwtUtil,
    private val redissonClient: RedissonClient,
    private val ticketService: TicketService
) {

    @Transactional
    fun deleteUser(userId: String): ResponseEntity<Any> {
        val user = findByUserId(userId)
        deletedUserRepository.save(user.toDeletedUser())
        user.userId += ":deleted:"+UUID.randomUUID().toString()
        user.phoneNumber = null
        user.address = null
        user.deletedDate = LocalDateTime.now()
        return ResponseEntity.ok().build<Any>()
    }

    @Transactional
    fun registerUserAddress(user: User, userAddress: UserAddressRequestDto) {
        user.address = userAddress.toDomain()
        userRepository.save(user)
    }

    @Transactional
    fun registerPhoneNumber(user: User, phoneNumberRequestDto: PhoneNumberRequestDto) {
        if(userRepository.existsByPhoneNumberIs(phoneNumberRequestDto.phoneNumber)) throw AlreadySavedPhoneNumber()
        user.phoneNumber = phoneNumberRequestDto.phoneNumber
        userRepository.save(user)
    }


    fun findByUserId(userId: String): User {
        return userRepository.findUserByUserId(userId)
            ?: run {
                println("not found loginId: $userId")
                throw GlobalExceptions.NotFoundException(NOT_FOUND) };
    }

    @Transactional
    fun saveTestUser(): String {
        val user = User(
            "testUser" + UUID.randomUUID().toString(),
            "testEmaiil" + UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        )
        userRepository.save(user)
        return jwtUtil.generateJwtToken(user.userId)
    }

    @Transactional
    fun ticketPlus(user: User, count: Int): Int {
        val userLock: RLock = redissonClient.getLock("userLock:${user.userId}")
        try {
            if (userLock.tryLock(10, TimeUnit.SECONDS)) {
                user.tickets += count
                ticketService.saveTicket(TicketHistory(user.userId,user.tickets))
                userRepository.save(user)
                return user.tickets
            } else {
                println("사용자 ID : ${user.userId}| 응모권 추가에 실패했습니다.")
                throw GlobalExceptions.InternalErrorException(USER_TICKET_LOCK_TIMEOUT)
            }
        } finally {
            if (userLock.isHeldByCurrentThread) {
                userLock.unlock()
            }
        }
    }

    @Transactional
    fun saveFcmToken(userId: String,fcmTokenDto: FcmTokenDto) {
        val user = findByUserId(userId)
        user.fcmToken = fcmTokenDto.token
    }

//    @Transactional
//    fun registerUser(authentication: OAuth2AuthenticationToken) {
//        val userDetails = authentication.principal.attributes
//        val userId = userDetails["sub"].toString()
//        val email = userDetails["email"]?.toString()
//        val name = userDetails["name"].toString()
//
//        val user = User(
//            userId = userId,
//            email = email,
//            name = name,
//            phoneNumber = "N/A" // 예시로 기본값 설정
//        )
//
//        userRepository.save(user)
//
//        address?.let {
//            it.user = user
//            addressRepository.save(it)
//        }
//    }
}