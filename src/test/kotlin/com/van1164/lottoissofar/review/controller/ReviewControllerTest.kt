package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.auth.service.AuthService
import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.fixture.builder.ReviewFixtureBuilder
import com.van1164.lottoissofar.fixture.UserFixture
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.user.service.UserService
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class ReviewControllerTest @Autowired constructor(
    val userRepository: UserJpaRepository,
    val reviewRepository: ReviewRepository,
    val mockMvc: MockMvc
){
    lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = UserFixture.createUser()
        userRepository.save(user)
    }

    @Test
    @WithMockUser
    fun getAll() {
        mockMvc.get("/api/v1/reviews") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
    }

    @Test
    fun getAllWithUser() {
    }

    @Test
    fun createNewReview() {
    }

}