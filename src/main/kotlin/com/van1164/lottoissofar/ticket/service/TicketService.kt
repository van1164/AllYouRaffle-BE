package com.van1164.lottoissofar.ticket.service

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.common.dto.ticket.response.TicketHistoryGraphResponse
import com.van1164.lottoissofar.common.exception.ErrorCode.INTERNAL_SERVER_ERROR
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.math.*


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

    fun getDeviationPage(userId: String, endDate: LocalDate?, cursor: Long, size: Int): TicketHistoryGraphResponse<DensityPoint> {
        val rawList = ticketHistoryRepository.findTicketHistoryGraph(userId, endDate, cursor, size)
        val logScaledIntervalList = rawList.content.zipWithNext {
            previous, current ->
            val durationMills = Duration.between(current.createdDate, previous.createdDate).toMillis()
            log10(durationMills.toDouble() + 1)
        }.sorted()

        if (logScaledIntervalList.size < 4) throw GlobalExceptions.InternalErrorException(
            INTERNAL_SERVER_ERROR
        )
        val q3Index = ((logScaledIntervalList.size - 1) * 3 / 4)
        val q1Index = ((logScaledIntervalList.size - 1) / 4)
        val sampledList = logScaledIntervalList.subList(q1Index, q3Index)

        val mean = sampledList.average()
        val standardDeviation = sampledList.map { it - mean }.map { it * it }.average().let { sqrt(it) }
        val deviation = logScaledIntervalList.map { it - mean }
        val bandwidth = 1.06 * standardDeviation * logScaledIntervalList.size.toDouble().pow(-0.2)
        val densityList = kernelDensityEstimation(deviation, bandwidth)

        return TicketHistoryGraphResponse(
            data = densityList,
            rawIntervalAverage = (10.0.pow(mean) - 1).toLong() / 1000,
            nextCursor = rawList.lastOrNull()?.id,
            hasNext = rawList.totalElements - size - 2 > 0
        )
    }

    private fun kernelDensityEstimation(data: List<Double>, bandwidth: Double): List<DensityPoint> {
        // 가우시안 커널을 적용하여 각 지점에 대한 밀도 계산
        return data.map { x ->
            val density = data.sumOf { xi ->
                val exponent = -0.5 * ((x - xi) / bandwidth).pow(2)
                exp(exponent) / (bandwidth * sqrt(2 * PI))
            } / data.size
            DensityPoint(x, density)
        }
    }

    data class DensityPoint(val deviation: Double, val density: Double)
}