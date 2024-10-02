package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.user.FcmTokenDto
import com.van1164.lottoissofar.common.dto.user.PhoneNumberRequestDto
import com.van1164.lottoissofar.common.dto.user.UserAddressRequestDto
import com.van1164.lottoissofar.user.repository.DeleteUserRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import kotlinx.datetime.LocalDateTime
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userService: UserService,
    private val userJpaRepository: UserJpaRepository,
    private val deleteUserRepository: DeleteUserRepository,
) {

    lateinit var user: User
    lateinit var deletedUser: User

    @BeforeEach
    fun setup() {
        userJpaRepository.deleteAll()
        user = User(
            "testUserId",
            "test",
            "test",
            "test",
            "test"
        )
        userJpaRepository.save(user)

        User(
            "testUserId2",
            "test",
            "test",
            "test",
            "test",
        ).run {
            this.deletedDate = java.time.LocalDateTime.now()
            deletedUser = userJpaRepository.save(this)
        }
    }

    @Test
    @DisplayName("사용자 검색 성공")
    fun findUserSuccess() {
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    fun deleteUserSuccess() {
        assertDoesNotThrow {
            val testUser = userService.findByUserId(user.userId)
        }
        userService.deleteUser(user.userId)
        assertEquals(deleteUserRepository.findByUserId(user.userId).count() ,1)
        val deleteUser = deleteUserRepository.findByUserId(user.userId)[0]
        assertEquals(deleteUser.formerId, user.id)
        assertEquals(deleteUser.email, user.email)

        val goId = userJpaRepository.findById(user.id).get()
        assertTrue("delete" in goId.userId)
    }

    @Test
    @DisplayName("삭제된 사용자 검색")
    fun findUserFailure() {
        val successUser = userJpaRepository.findById(deletedUser.id)
        assertTrue(successUser.isPresent)

        val currentUser = userJpaRepository.findUserByUserId(deletedUser.userId)
        assertNull(currentUser)
    }


    @Test
    @DisplayName("주소 저장 테스트")
    fun registerUserAddress() {
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
        assertNull(currentUser.address)

        val userAddress = UserAddressRequestDto(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            true
        )
        userService.registerUserAddress(user, userAddress)

        val thenUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(thenUser)
        assertNotNull(thenUser.address)
    }

    @Test
    @DisplayName("전화번호 저장 테스트")
    fun registerPhoneNumber() {
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
        assertNull(currentUser.phoneNumber)

        //when
        val phoneNumber = "testPhoneNumber"
        userService.registerPhoneNumber(user, PhoneNumberRequestDto(phoneNumber))

        val thenUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(thenUser)
        assertNotNull(thenUser.phoneNumber)
        assertEquals(phoneNumber, thenUser.phoneNumber)
    }

    @Test
    @DisplayName("토큰 저장 테스트")
    fun saveFcmTokenSuccess() {
        val currentUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(currentUser)
        assertNull(currentUser.fcmToken)

        //when
        val fcmToken = "testToken"
        userService.saveFcmToken(currentUser.userId, FcmTokenDto(fcmToken))

        val thenUser = userJpaRepository.findUserByUserId(user.userId)
        assertNotNull(thenUser)
        assertNotNull(thenUser.fcmToken)
        assertEquals(fcmToken, thenUser.fcmToken)
    }
}