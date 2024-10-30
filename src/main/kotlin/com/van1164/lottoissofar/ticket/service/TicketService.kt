package com.van1164.lottoissofar.ticket.service

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.common.exception.ErrorCode.INTERNAL_SERVER_ERROR
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.log10


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

    fun getDeviationPage(userId: String, endDate: LocalDate?, cursor: Long, size: Int): CursorPage<Double> {
        val result = ticketHistoryRepository.findTicketHistoryGraph(userId, endDate, cursor, size)
        val logScaledIntervalList = result.content.zipWithNext {
            previous, current ->
            val durationMills = Duration.between(current.createdDate, previous.createdDate).toMillis()
            log10(durationMills.toDouble() + 1)
        }.sorted()

        if (logScaledIntervalList.size < 4) throw GlobalExceptions.InternalErrorException(
            INTERNAL_SERVER_ERROR
        )
        val q3Index = ((logScaledIntervalList.size - 1) * 3 / 4)
        val q1Index = ((logScaledIntervalList.size - 1) / 4)
        val sampledAverage = logScaledIntervalList.subList(q1Index, q3Index).average()
        val deviationFromAverage = logScaledIntervalList.map { it - sampledAverage }

        return CursorPage(
            data = deviationFromAverage,
            nextCursor = result.lastOrNull()?.id,
            hasNext = result.totalElements - size - 2 > 0
        )
    }
}