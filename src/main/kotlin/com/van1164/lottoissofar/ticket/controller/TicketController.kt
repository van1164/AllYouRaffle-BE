package com.van1164.lottoissofar.ticket.controller

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.common.dto.ticket.response.TicketHistoryGraphResponse
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.ticket.service.TicketService
import org.springframework.web.bind.annotation.*
import sun.security.krb5.internal.Ticket
import java.time.LocalDate


@RestController
@RequestMapping("/api/v1/tickets")
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("/last_1h/{userId}")
    fun getTicketsLast1h(@PathVariable userId: String): List<TicketHistory> {
        return ticketService.getTicketsLast1h(userId)
    }

    @GetMapping("/interval-deviation/{userId}")
    fun getIntervalDeviation(
        @PathVariable userId: String,
        @RequestParam(required = false) endDate: LocalDate?,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "10") size: Int
    ): TicketHistoryGraphResponse<TicketService.DensityPoint> {
        val effectiveCursor = cursor ?: Long.MAX_VALUE

        return ticketService.getDeviationPage(userId, endDate, effectiveCursor, size)
    }
}