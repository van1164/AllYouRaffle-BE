package com.van1164.lottoissofar.common.dto.notification



data class NotificationResponseDto(
    val title : String,
    val body: String,
    val createdDate : java.time.LocalDateTime,
)
