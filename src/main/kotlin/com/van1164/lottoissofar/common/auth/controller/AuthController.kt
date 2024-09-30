package com.van1164.lottoissofar.common.auth.controller

import com.van1164.lottoissofar.common.auth.service.AuthService
import com.van1164.lottoissofar.common.dto.user.*
import com.van1164.lottoissofar.common.security.CustomOAuth2User
import com.van1164.lottoissofar.common.security.CustomOAuth2UserService
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.user.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/login")
class AuthController(
    private val jwtUtil: JwtUtil,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/verify_phone")
    fun verifyPhone(
        @RequestBody phoneNumber : PhoneNumberVerifyDto
    ): ResponseEntity<Any> {
        return  authService.verifyPhoneNumber(phoneNumber.phoneNumber)
    }

    @GetMapping("/oauth2/success")
    fun oauth2LoginSuccess(authentication: Authentication): String {
        val oauth2User = authentication.principal as CustomOAuth2User
        val userId = oauth2User.userId

        // JWT 생성
        val token = jwtUtil.generateJwtToken(userId)

        // JWT를 프론트엔드에 전송 (여기서는 문자열로 반환)
        return token
    }


    @PostMapping("/apple")
    fun appleLogin(@RequestBody appleLoginDto : AppleLoginDto) : MobileLoginResponse {
        userService.findByUserId("apple:${appleLoginDto.userId}")

        return createMobileLoginResponse("apple:${appleLoginDto.userId}")
    }

    @PostMapping("/apple/signup")
    fun appleSignUp(@RequestBody mobileUserLoginDto: AppleSignupDto) : MobileLoginResponse {
        val oAuthUser = customOAuth2UserService.customOAuth2UserForApple(
            name = mobileUserLoginDto.name,
            email = mobileUserLoginDto.email,
            profileImageUrl = mobileUserLoginDto.profileImageUrl,
            userNameAttributeNameValue = mobileUserLoginDto.userNameAttributeNameValue,
            appleId = mobileUserLoginDto.userId
        )
        return createMobileLoginResponse(oAuthUser.userId)
    }

    @PostMapping("")
    fun mobileLogin(@RequestBody mobileUserLoginDto: MobileUserLoginDto): MobileLoginResponse {
         val oAuthUser = customOAuth2UserService.customOAuth2User(
             name = mobileUserLoginDto.name,
             email = mobileUserLoginDto.email,
             registrationId = mobileUserLoginDto.registrationId,
             profileImageUrl = mobileUserLoginDto.profileImageUrl,
             userNameAttributeNameValue = mobileUserLoginDto.userNameAttributeNameValue
         )
        return createMobileLoginResponse(oAuthUser.userId)
    }

    private fun createMobileLoginResponse(userId : String): MobileLoginResponse {
        return MobileLoginResponse(
            jwt =  jwtUtil.generateJwtToken(userId),
            refreshToken = jwtUtil.generateRefreshToken(userId)
        )
    }

    @PostMapping("/refresh")
    fun refresh(@RequestParam("refreshToken") refreshToken : String): JwtTokenResponse {
        return JwtTokenResponse(
            authService.createNewToken(refreshToken)
        )

    }

    @PostMapping("/verify")
    fun verify(@RequestParam("jwt") jwt : String): ResponseEntity<Any> {
        if(!jwtUtil.validateToken(jwt,jwtUtil.extractUsername(jwt))) return ResponseEntity.badRequest().build<Any>()
       return ResponseEntity.ok().build()
    }


}