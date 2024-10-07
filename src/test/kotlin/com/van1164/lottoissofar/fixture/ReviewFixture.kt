package com.van1164.lottoissofar.fixture

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import java.util.*

class ReviewFixture {
    companion object {
        fun createReview(user: User): Review {
            return ReviewFixture().review(user = user) {}
        }
    }

    fun review(user: User, block: Review.() -> Unit = {}): Review {
        val review = Review(
            title = UUID.randomUUID().toString(),
            description = UUID.randomUUID().toString(),
            imageUrl = UUID.randomUUID().toString(),
            user = user
        )
        review.apply(block)
        return review
    }

}