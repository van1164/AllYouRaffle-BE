package com.van1164.lottoissofar.winner_history.repository

import com.van1164.lottoissofar.common.dto.winner_history.ReadWinnerHistoryDto
import org.springframework.data.domain.Page

interface WinnerHistoryRepositoryCustom {
    fun findByUserId(userId: String, cursor: Long?, size: Int): Page<ReadWinnerHistoryDto>
}