package com.van1164.lottoissofar.common.security

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.user.service.UserService
import org.springframework.core.MethodParameter
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserArgumentResolver(
    private val userService: UserService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        println(parameter.parameterType)
        println(parameter.parameterType == User::class.java)
        return parameter.parameterType == User::class.java
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): User? {
        val authentication = SecurityContextHolder.getContext().authentication
        println("NNNNNNNNNNNNNNNNNNNNNNNNNN"+authentication)
        if (authentication != null && authentication.isAuthenticated) {
            val principal = authentication.principal
            println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV")
            println(principal)
            if (principal is CustomUserDetails) {
                val userId = principal.loginId
                println(userId)
                return userService.findByUserId(userId)
            }
        }
        return null
    }
}