package com.van1164.lottoissofar.common.dto.review

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User

data class CreateReviewDto (
    val title: String,
    val description: String,
    val imageUrl: String? = null
) {
    fun toDomain(user: User) : Review {
        return Review(title, description, imageUrl, user)
    }
}