package com.van1164.lottoissofar.common.auth.service

import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userJpaRepository: UserJpaRepository,
    private val jwtUtil: JwtUtil
) {
    fun createNewToken(refreshToken: String): String {
        val userId = jwtUtil.extractUsername(refreshToken)
        return userJpaRepository.findUserByUserId(userId).let { user ->
            if (user == null) throw GlobalExceptions.NotFoundException("사용자를 찾을 수 없습니다.")
            return@let jwtUtil.generateJwtToken(userId)
        }
    }

}