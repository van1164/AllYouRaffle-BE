package com.van1164.lottoissofar.ticket.repository

import com.van1164.lottoissofar.common.dto.ticket.TicketHistoryGraphRequirementsDto
import org.springframework.data.domain.Page
import java.time.LocalDate

interface TicketHistoryRepositoryCustom {
    fun findTicketHistoryGraph(userId: String, endDate: LocalDate?, cursor:Long, size:Int): Page<TicketHistoryGraphRequirementsDto>
}