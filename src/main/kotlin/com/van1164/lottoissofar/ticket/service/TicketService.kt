package com.van1164.lottoissofar.ticket.service

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import sun.security.krb5.internal.Ticket
import java.time.LocalDateTime


@Service
class TicketService(
    private val ticketHistoryRepository: TicketHistoryRepository
) {
    @Transactional
    fun saveTicket(ticketHistory: TicketHistory) {
        ticketHistoryRepository.save(ticketHistory)
    }

    fun getTicketsLast1h(userId: String): List<TicketHistory> {
        return ticketHistoryRepository.findAllByUserIdAndCreatedDateAfter(userId, LocalDateTime.now().minusHours(1))
    }
}