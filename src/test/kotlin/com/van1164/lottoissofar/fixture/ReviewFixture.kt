package com.van1164.lottoissofar.fixture

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.WinnerHistory
import java.util.*

class ReviewFixture {
    companion object {
        fun createReview(user: User, winnerHistory: WinnerHistory): Review {
            return ReviewFixture().review(
                user = user,
                winnerHistory = winnerHistory
            ) {}
        }
    }

    fun review(user: User, winnerHistory: WinnerHistory, block: Review.() -> Unit = {}): Review {
        val review = Review(
            title = UUID.randomUUID().toString(),
            description = UUID.randomUUID().toString(),
            imageUrl = UUID.randomUUID().toString(),
            user = user,
            winnerHistory = winnerHistory
        )
        review.apply(block)
        return review
    }

}