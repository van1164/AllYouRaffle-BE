package com.van1164.lottoissofar.common.dto.winner_history

import java.time.LocalDateTime

data class ReadWinnerHistoryDto (
    val id: Long,
    val userId: String,
    val raffleId: Long,
    val itemName: String,
    val itemImageUrl: String,
    val raffleCompletedDate: LocalDateTime,
    val reviewTitle: String? = null,
    val reviewDescription: String? = null,
    val reviewImageUrl: String? = null,
)