package com.van1164.lottoissofar.common.dto.winner_history.response

import com.van1164.lottoissofar.common.dto.winner_history.ReadWinnerHistoryDto
import java.time.LocalDateTime

data class ReadWinnerHistoryResponse (
    val id: Long,
    val itemName: String,
    val itemImageUrl: String,
    val raffleCompletedDate: LocalDateTime,
    val reviewTitle: String?,
    val reviewDescription: String?,
    val reviewImageUrl: String?
) {
    companion object {
        fun of(dto: ReadWinnerHistoryDto): ReadWinnerHistoryResponse {
            return ReadWinnerHistoryResponse(
                id = dto.id,
                itemName = dto.itemName,
                itemImageUrl = dto.itemImageUrl,
                raffleCompletedDate = dto.raffleCompletedDate,
                reviewTitle = dto.reviewTitle,
                reviewDescription = dto.reviewDescription,
                reviewImageUrl = dto.reviewImageUrl
            )
        }
    }
}