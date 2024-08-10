package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.Role
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.UserAddress
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userJpaRepository: UserJpaRepository
) {

    lateinit var user : User
    @BeforeEach
    fun setup(){
        userJpaRepository.deleteAll()
        user = User(
            "testUserId",
            "test",
            "test",
            "test",
            "test"
        )
        userJpaRepository.save(user)
    }

    @Test
    @DisplayName("주소 저장 테스트")
    fun registerUserAddress(){
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
        assertNull(currentUser.address)

        val userAddress = UserAddressRequestDto(
            "tt",
            "tt",
            "te",
            "sd",
            "sdf",
            true
        )
        userService.registerUserAddress(user,userAddress)

        val thenUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(thenUser)
        assertNotNull(thenUser.address)
    }

    @Test
    @DisplayName("전화번호 저장 테스트")
    fun registerPhoneNumber(){
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
        assertNull(currentUser.phoneNumber)

        //when
        val phoneNumber = "testPhoneNumber"
        userService.registerPhoneNumber(user, PhoneNumberRequestDto(phoneNumber))

        val thenUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(thenUser)
        assertNotNull(thenUser.phoneNumber)
        assertEquals(phoneNumber,thenUser.phoneNumber)
    }
}