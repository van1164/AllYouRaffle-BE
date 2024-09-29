package com.van1164.lottoissofar.raffle.controller

import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.raffle.service.RaffleService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/raffle")
class RaffleControllerV2(
    private val raffleService: RaffleService
) {

    @GetMapping("/active/free")
    fun getActiveFree(
        @PageableDefault pageable: Pageable
    ): Page<Raffle> {
        return raffleService.getActiveFreeRaffle(pageable)
    }
}