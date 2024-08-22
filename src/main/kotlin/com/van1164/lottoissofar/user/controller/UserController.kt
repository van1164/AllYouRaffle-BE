package com.van1164.lottoissofar.user.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.UserAddress
import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    val userService: UserService
) {
    @GetMapping("/mypage")
    fun getUserPage(
        @Parameter(hidden = true)
        user: User
    ): User {
        return user
    }

    @GetMapping("/tickets")
    fun getUserTickets(
        @Parameter(hidden = true)
        user: User
    ): Int {
        return user.tickets
    }

    @GetMapping("/tickets/plus_one")
    fun ticketsPlusOne(
        @Parameter(hidden = true)
        user: User
    ) {
        userService.ticketPlus(user,1)
    }

    @PostMapping("/set_address")
    fun setAddress(
        @Parameter(hidden = true)
        user: User,
        @RequestBody userAddressDto: UserAddressRequestDto
    ) {
        userService.registerUserAddress(user, userAddressDto)
    }

    @PostMapping("/set_phoneNumber")
    fun setAddress(
        @Parameter(hidden = true)
        user: User,
        @RequestBody phoneNumber: PhoneNumberRequestDto
    ): ResponseEntity<Any> {
        try {
            userService.registerPhoneNumber(user, phoneNumber)
            return ResponseEntity.ok().build()
        } catch (e: ConstraintViolationException) {
            return ResponseEntity.badRequest().body(
                ErrorResponse(
                    message = "같은 번호로 등록된 아이디가 있습니다.",
                    description = "같은 번호로 등록된 아이디가 있습니다."
                )
            )
        }

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