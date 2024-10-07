package com.van1164.lottoissofar.common.dto.review

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.WinnerHistory

data class CreateReviewDto (
    val winnerHistoryId: Long,
    val title: String,
    val description: String,
) {
    fun toDomain(user: User, winnerHistory: WinnerHistory, imageUrl: String?) : Review {
        return Review (
            winnerHistory = winnerHistory,
            title = title,
            description = description,
            imageUrl = imageUrl,
            user = user
        )
    }
}