package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.fixture.UserFixture
import com.van1164.lottoissofar.fixture.builder.UserFixtureBuilder
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReviewControllerTest @Autowired constructor(
    val userRepository: UserJpaRepository,
    val reviewRepository: ReviewRepository
){
    lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = UserFixture.createUser()
        userRepository.save(user)
    }

    @Test
    fun getAll() {
    }

    @Test
    fun getAllWithUser() {
    }

    @Test
    fun createNewReview() {
    }
}