package com.van1164.lottoissofar.winner_history.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.winner_history.response.ReadWinnerHistoryResponse
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.winner_history.service.WinnerHistoryService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class WinnerHistoryController(
    private val winnerHistoryService: WinnerHistoryService
) {
    @GetMapping("winner_history")
    fun findByUserId(
        @Parameter(hidden = true) user: User,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): CursorPage<ReadWinnerHistoryResponse> {
        return winnerHistoryService.findByUserId(user.userId, cursor, size)
    }
}