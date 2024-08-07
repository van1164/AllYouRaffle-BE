package com.van1164.lottoissofar.user.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService
) {
    @GetMapping("/mypage")
    fun getUserPage(user : User): User {
        return user
    }

    @Operation(
        summary = "임시 사용자 추가"
    )
    @ApiResponse(description = "jwt 반환")
    @PostMapping("/create_test_user")
    fun createTestUser(): String {
        return userService.saveTestUser()
    }
}