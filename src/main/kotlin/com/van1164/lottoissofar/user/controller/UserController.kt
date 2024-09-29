package com.van1164.lottoissofar.user.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.user.exception.AlreadySavedPhoneNumber
import com.van1164.lottoissofar.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    @PostMapping("/tickets/plus_one")
    fun ticketsPlusOne(
        @Parameter(hidden = true)
        user: User
    ): Int {
        return userService.ticketPlus(user,1)
    }

    @PostMapping("/tickets/plus/{reward}")
    fun ticketsPlus(
        @Parameter(hidden = true)
        user: User,
        @PathVariable(name= "reward") reward: Int
    ): Int {
        return userService.ticketPlus(user,reward)
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
    fun setPhoneNumber(
        @Parameter(hidden = true)
        user: User,
        @RequestBody phoneNumber: PhoneNumberRequestDto
    ): ResponseEntity<Any> {
        try {
            userService.registerPhoneNumber(user, phoneNumber)
            return ResponseEntity.ok().build()
        } catch (e: AlreadySavedPhoneNumber) {
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