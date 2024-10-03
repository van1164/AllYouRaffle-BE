package com.van1164.lottoissofar.review.repository

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.fixture.UserFixture
import com.van1164.lottoissofar.fixture.builder.ReviewFixtureBuilder
import com.van1164.lottoissofar.fixture.builder.UserFixtureBuilder
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReviewRepositoryImplTest @Autowired constructor(
    val reviewRepository: ReviewRepository,
    val userRepository: UserJpaRepository
){
    lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = UserFixture.createUser()
        userRepository.save(user)
    }

    @Test
    @DisplayName("findAllPaged는 요청 개수만큼 생성 역순으로 정렬한 결과를 반환해야 한다.")
    fun findAllPaged() {
        val review1: Review = ReviewFixtureBuilder().user(user).title("리뷰 1").build()
        val review2: Review = ReviewFixtureBuilder().user(user).title("리뷰 2").build()
        reviewRepository.save(review1)
        reviewRepository.save(review2)
        val cursor: Long = Long.MAX_VALUE
        val size = 2

        val result = reviewRepository.findAllPaged(cursor, size)

        assertThat(result.content).hasSize(2)
        assertThat(result.content[0].title).isEqualTo("리뷰 2")
    }

    @Test
    @DisplayName("findAllPagedWithUser는 User가 생성한 결과만 반환해야 한다")
    fun findAllPagedWithUser() {
        val user2: User = UserFixtureBuilder().email("user2@test.com").build()
        userRepository.save(user2)
        val review1: Review = ReviewFixtureBuilder().user(user).title("리뷰 1").build()
        val review2: Review = ReviewFixtureBuilder().user(user2).title("리뷰 2").build()
        reviewRepository.save(review1)
        reviewRepository.save(review2)
        val cursor: Long = Long.MAX_VALUE
        val size = 2

        val result = reviewRepository.findAllPagedWithUser(user2, cursor, size)

        assertThat(result.content).hasSize(1)
        assertThat(result.content[0].title).isEqualTo("리뷰 2")
    }
}