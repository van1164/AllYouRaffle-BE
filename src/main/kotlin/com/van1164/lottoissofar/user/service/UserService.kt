package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.UserAddress
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserJpaRepository,
) {
    @Transactional
    fun registerUserAddress(userId: String, userAddress: UserAddress) {
        findByUserId(userId).apply {
            this.address = userAddress
            userAddress.user = this
        }
    }

    @Transactional
    fun registerPhoneNumber(userId: String, phoneNumber : String) {
        findByUserId(userId).apply {
            this.phoneNumber = phoneNumber
        }
    }


    fun findByUserId(userId: String): User {
        return userRepository.findUserByUserId(userId)
            ?: run { throw GlobalExceptions.NotFoundException("not found loginId : $userId") };
    }

//    @Transactional
//    fun registerUser(authentication: OAuth2AuthenticationToken) {
//        val userDetails = authentication.principal.attributes
//        val userId = userDetails["sub"].toString()
//        val email = userDetails["email"]?.toString()
//        val name = userDetails["name"].toString()
//
//        val user = User(
//            userId = userId,
//            email = email,
//            name = name,
//            phoneNumber = "N/A" // 예시로 기본값 설정
//        )
//
//        userRepository.save(user)
//
//        address?.let {
//            it.user = user
//            addressRepository.save(it)
//        }
//    }
}