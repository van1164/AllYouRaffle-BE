package com.van1164.lottoissofar.common.dto.ticket

import com.van1164.lottoissofar.common.domain.TicketHistory
import java.time.LocalDateTime

data class TicketHistoryGraphRequirementsDto (
    val id: Long,
    val userId: String,
    val ticketCount: Int,
    val createdDate: LocalDateTime?
) {
    constructor(ticketHistory: TicketHistory) : this (
        id = ticketHistory.id,
        userId = ticketHistory.userId,
        ticketCount = ticketHistory.ticketCount,
        createdDate = ticketHistory.createdDate
    )
}