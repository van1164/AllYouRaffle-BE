package com.van1164.lottoissofar.common.controller

import com.van1164.lottoissofar.common.security.CustomOAuth2User
import com.van1164.lottoissofar.common.security.JwtUtil
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
    private val jwtUtil: JwtUtil
) {
    @GetMapping("/login/oauth2/success")
    fun oauth2LoginSuccess(authentication: Authentication): String {
        val oauth2User = authentication.principal as CustomOAuth2User
        val userId = oauth2User.userId

        // JWT 생성
        val token = jwtUtil.generateToken(userId)

        // JWT를 프론트엔드에 전송 (여기서는 문자열로 반환)
        return token
    }
}