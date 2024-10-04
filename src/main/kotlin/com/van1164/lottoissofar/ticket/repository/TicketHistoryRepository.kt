package com.van1164.lottoissofar.ticket.repository

import com.van1164.lottoissofar.common.domain.TicketHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TicketHistoryRepository : JpaRepository<TicketHistory, Long> {
    fun findAllByUserIdOrderByCreatedDateDesc(userId: String): List<TicketHistory>
    fun findAllByUserIdAndCreatedDateAfter(userId: String, findTime:LocalDateTime ): List<TicketHistory>
}