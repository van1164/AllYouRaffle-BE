package com.van1164.lottoissofar.winner_history.service

import com.van1164.lottoissofar.common.domain.WinnerHistory
import com.van1164.lottoissofar.common.domain.WinnerHistoryStatus
import com.van1164.lottoissofar.common.dto.winner_history.response.ReadWinnerHistoryResponse
import com.van1164.lottoissofar.common.exception.ErrorCode
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.winner_history.repository.WinnerHistoryRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors


@Service
class WinnerHistoryService(
    val winnerHistoryRepository: WinnerHistoryRepository
) {
    fun findByUserId(
        userId: String,
        cursor: Long?,
        size: Int
    ): CursorPage<ReadWinnerHistoryResponse> {
        val effectiveCursor = cursor ?: Long.MAX_VALUE
        val result = winnerHistoryRepository.findByUserId(userId, effectiveCursor, size)
            .map { ReadWinnerHistoryResponse.of(it) }
        val nextCursor = result.content.lastOrNull()?.id

        return CursorPage(result.content, nextCursor, result.hasNext())
    }

    @Transactional
    fun saveWinnerHistory(winnerHistory: WinnerHistory) {
        winnerHistoryRepository.save(winnerHistory)
    }

    @Transactional
    fun changeStatus(historyId: Long, winnerHistoryStatus: WinnerHistoryStatus) {
        val history = findById(historyId)
        history.status = winnerHistoryStatus
    }

    fun findById(historyId: Long): WinnerHistory {
        return winnerHistoryRepository.findById(historyId)
            .orElseThrow { GlobalExceptions.NotFoundException(ErrorCode.NOT_FOUND) }
    }

}