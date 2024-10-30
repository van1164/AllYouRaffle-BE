package com.van1164.lottoissofar.common.dto.ticket.response

data class TicketHistoryGraphResponse<T> (
    val data: List<T>,
    val rawIntervalAverage: Long,
    val nextCursor: Long?,
    val hasNext: Boolean
)