package com.van1164.lottoissofar.test

import com.van1164.lottoissofar.common.security.JwtUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView


@RestController
class TestController(
    private val jwtUtil: JwtUtil
) {
    @GetMapping("/test/login/google")
    fun testGoogleLogin(): RedirectView {
        val jwt = jwtUtil.generateJwtToken("Admin")
        val refresh = jwtUtil.generateRefreshToken("Admin")

        // 리다이렉트할 URL
        val redirectUrl = "http://localhost:3000?access_token=$jwt&refresh_token=$refresh"


        // RedirectView를 통해 리다이렉트
        val redirectView = RedirectView()
        redirectView.url = redirectUrl
        return redirectView
    }
}