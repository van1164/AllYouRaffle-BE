package com.van1164.lottoissofar.common.dto.winner_history

data class ReadWinnerHistoryDto (
    val id: Long,
    val userId: String,
    val reviewTitle: String,
    val reviewDescription: String,
    val reviewImageUrl: String
)