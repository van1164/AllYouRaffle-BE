package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID
import java.util.concurrent.TimeUnit

@Service
class UserService(
    private val userRepository: UserJpaRepository,
    private val jwtUtil: JwtUtil,
    private val redissonClient: RedissonClient,
) {
    @Transactional
    fun registerUserAddress(user: User, userAddress: UserAddressRequestDto) {
        user.address = userAddress.toDomain()
    }

    @Transactional
    fun registerPhoneNumber(user: User, phoneNumberRequestDto: PhoneNumberRequestDto) {
        user.phoneNumber = phoneNumberRequestDto.phoneNumber
    }


    fun findByUserId(userId: String): User {
        return userRepository.findUserByUserId(userId)
            ?: run { throw GlobalExceptions.NotFoundException("not found loginId : $userId") };
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
    fun ticketPlus(user: User, count: Int) {
        val userLock: RLock = redissonClient.getLock("userLock:${user.userId}")
        try {
            if (userLock.tryLock(10, TimeUnit.SECONDS)) {
                user.tickets += 1
                userRepository.save(user)
            } else {
                throw GlobalExceptions.InternalErrorException("사용자 ID : ${user.userId}|응모권 추가에 실패하였습니다.")
            }
        } finally {
            if (userLock.isHeldByCurrentThread) {
                userLock.unlock()
            }
        }
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