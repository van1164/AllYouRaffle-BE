package com.van1164.lottoissofar.winner_history.repository

import com.van1164.lottoissofar.common.domain.WinnerHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WinnerHistoryRepository : JpaRepository<WinnerHistory, Long>, WinnerHistoryRepositoryCustom {
}