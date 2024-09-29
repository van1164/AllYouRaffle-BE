package com.van1164.lottoissofar.common.dto.review

import com.van1164.lottoissofar.common.domain.Review

data class ReadReviewDto(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String?

) {
    constructor(review: Review) : this (
        id = review.id,
        title = review.title,
        description = review.description,
        imageUrl = review.imageUrl
    )
}
