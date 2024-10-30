package com.van1164.lottoissofar.ticket.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QTicketHistory.ticketHistory
import com.van1164.lottoissofar.common.dto.ticket.TicketHistoryGraphRequirementsDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils
import java.time.LocalDate

class TicketHistoryRepositoryImpl (
    em: EntityManager
) : TicketHistoryRepositoryCustom {
    private val query = JPAQueryFactory(em)
    override fun findTicketHistoryGraph(userId: String, endDate: LocalDate?, cursor: Long, size: Int): Page<TicketHistoryGraphRequirementsDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        TicketHistoryGraphRequirementsDto::class.java,
                        ticketHistory
                    )
            )
            .from(ticketHistory)
            .where(
                ticketHistory.userId.eq(userId),
                endDate?.let { ticketHistory.createdDate.before(it.plusDays(1L).atStartOfDay()) },
                ticketHistory.id.lt(cursor)
            )
            .orderBy(ticketHistory.id.desc())
            .limit(size.toLong())
            .fetch()

        val countQuery = {
            query
                .select(ticketHistory.count())
                .from(ticketHistory)
                .where(
                    ticketHistory.userId.eq(userId),
                    endDate?.let { ticketHistory.createdDate.before(it.plusDays(1L).atStartOfDay()) },
                    ticketHistory.id.lt(cursor)
                )
                .fetchOne()?: 0L
        }

        return PageableExecutionUtils.getPage(list, PageRequest.ofSize(size), countQuery)
    }
}