package com.van1164.lottoissofar.common.security

import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException


@Component
class OAuthSuccessHandler(
    @Value("\${secretKey}")
    private val jwtSecret: String,
    @Value("\${cors.frontend}")
    private val corsFrontend : String,

    private val jwtUtil: JwtUtil
) : SimpleUrlAuthenticationSuccessHandler() {


    /**
     * oauth 로그인 성공시 JWT Token 생성해서 리다이렉트 응답.
     */
    @Throws(IOException::class, ServletException::class)
    override fun onAuthenticationSuccess(
        request: HttpServletRequest, response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user = authentication.principal as CustomOAuth2User
        val userId = user.userId
        println(userId)
        val accessToken: String = jwtUtil.generateJwtToken(
            username = userId
        )

//        val cookie: ResponseCookie = ResponseCookie.from(JWTUtil.REFRESH_TOKEN, refreshToken)
//            .httpOnly(true)
//            .secure(true)
//            .path("/") // path
//            .maxAge(Duration.ofDays(15))
//            .sameSite("None") // sameSite
//            .build()
//        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString())

        response.sendRedirect(
            corsFrontend +
                    "?access_token=" + accessToken
        )
    }

}