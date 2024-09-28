package com.van1164.lottoissofar.common.security

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.user.service.UserService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailService(
    val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(loginId : String): UserDetails {
        val user = userService.findByUserId(loginId)
        val userDetails = userToUserDetails(user)
        return userDetails
    }

    private fun userToUserDetails(user : User): CustomUserDetails {
        return CustomUserDetails(
            email = user.email,
            loginId = user.userId,
            id = user.id,
            nickname = user.nickname,
            password = user.password,
            addressVerified = user.address != null,
            phoneNumberVerified = user.phoneNumber != null,
            authorities = setOf(SimpleGrantedAuthority(user.role.key))
        )
    }
}