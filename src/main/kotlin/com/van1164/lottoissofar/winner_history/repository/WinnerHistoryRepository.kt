package com.van1164.lottoissofar.winner_history.repository

import com.van1164.lottoissofar.common.domain.WinnerHistory
import org.springframework.data.jpa.repository.JpaRepository

interface WinnerHistoryRepository : JpaRepository<WinnerHistory, Long> {
    fun findByUserId(userId: String): List<WinnerHistory>
}