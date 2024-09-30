package com.van1164.lottoissofar.user.repository

import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class UserRepositoryTest @Autowired constructor(
    val userJpaRepository: UserJpaRepository
) {

    @BeforeEach
    fun beforeEach() {
        userJpaRepository.deleteAll()
    }

    @Test
    @DisplayName("사용자 없을 때")
    fun findByUserIdNotFound(){
        val noUser = userJpaRepository.findUserByUserId("test")
        assertNull(noUser)
    }
}