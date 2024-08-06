package com.van1164.lottoissofar.user.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.user.service.UserService
import org.springframework.web.bind.annotation.GetMapping
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
}