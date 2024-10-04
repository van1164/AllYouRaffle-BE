package com.van1164.lottoissofar.ticket.controller

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.ticket.service.TicketService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sun.security.krb5.internal.Ticket


@RestController
@RequestMapping("/api/v1/tickets")
class TicketController(
    private val ticketService: TicketService
) {

    @GetMapping("/last_1h/{userId}")
    fun getTicketsLast1h(@PathVariable userId: String): List<TicketHistory> {
        return ticketService.getTicketsLast1h(userId)
    }
}