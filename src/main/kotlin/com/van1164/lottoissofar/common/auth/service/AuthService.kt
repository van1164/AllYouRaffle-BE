package com.van1164.lottoissofar.common.auth.service

import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import com.van1164.lottoissofar.common.dto.sms.SmsMessageDto
import com.van1164.lottoissofar.common.exception.ErrorCode
import com.van1164.lottoissofar.common.exception.ErrorCode.*
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.sms.SmsService
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class AuthService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtUtil: JwtUtil,
    private val smsService: SmsService
) {
    fun createNewToken(refreshToken: String): String {
        val userId = jwtUtil.extractUsername(refreshToken)
        return userJpaRepository.findUserByUserId(userId).let { user ->
            if (user == null) throw GlobalExceptions.NotFoundException(USER_NOT_FOUND)
            return@let jwtUtil.generateJwtToken(userId)
        }
    }

    fun verifyPhoneNumber(phoneNumber: String): ResponseEntity<Any> {
        if (userJpaRepository.existsByPhoneNumberIs(phoneNumber)) return ResponseEntity.badRequest().body(
            ErrorResponse(
                "이미 등록된 전화번호입니다.",
                "이미 등록된 전화번호입니다."
            )
        )
        val secretNumber = generateRandomNumber()
        val smsMessageDto = SmsMessageDto(
            to = phoneNumber,
            message = "[올유레플] 인증번호 [$secretNumber]\n절대 타인에게 알려주지 마세요."
        )
        val response = smsService.sendOne(smsMessageDto)
        if (response == null) throw GlobalExceptions.InternalErrorException(MESSAGE_SEND_FAIL)
        else return ResponseEntity.ok(hashMapOf("secretKey" to secretNumber))
    }

    private fun generateRandomNumber(): String {
        val randomNumber = Random.nextInt(0, 1000000) // 0부터 999999까지 랜덤 생성
        return String.format("%06d", randomNumber) // 6자리로 포맷팅
    }
}