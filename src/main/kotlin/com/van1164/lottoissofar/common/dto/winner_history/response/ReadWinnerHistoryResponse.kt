package com.van1164.lottoissofar.common.dto.winner_history.response

import com.van1164.lottoissofar.common.dto.winner_history.ReadWinnerHistoryDto

data class ReadWinnerHistoryResponse (
    val id: Long,
    val reviewTitle: String,
    val reviewDescription: String,
    val reviewImageUrl: String
) {
    companion object {
        fun of(dto: ReadWinnerHistoryDto): ReadWinnerHistoryResponse {
            return ReadWinnerHistoryResponse(
                id = dto.id,
                reviewTitle = dto.reviewTitle,
                reviewDescription = dto.reviewDescription,
                reviewImageUrl = dto.reviewImageUrl
            )
        }
    }
}