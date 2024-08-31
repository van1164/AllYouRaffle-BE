package com.van1164.lottoissofar.common.dto.review

import com.van1164.lottoissofar.common.domain.Review

data class ReadReviewDto(
    val title: String,
    val description: String,
    val imageUrl: String?

) {
    constructor(review: Review) : this (
        title = review.title,
        description = review.description,
        imageUrl = review.imageUrl
    )
}
