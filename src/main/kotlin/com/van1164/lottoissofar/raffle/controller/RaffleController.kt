package com.van1164.lottoissofar.raffle.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.security.CustomUserDetails
import com.van1164.lottoissofar.raffle.service.RaffleService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/raffle")
class RaffleController(
    private val raffleService: RaffleService
) {

    @PostMapping("/purchase/{raffleId}")
    fun purchase(
        @PathVariable(value = "raffleId") raffleId: Long,
        @Parameter(hidden = true)
        user: User
    ): ResponseEntity<PurchaseHistory> {
        return raffleService.purchaseRaffle(raffleId, user)
    }

    @GetMapping("/active")
    fun getActive(
        @PageableDefault pageable: Pageable
    ): Page<Raffle> {
        return raffleService.getActive(pageable)
    }

    @GetMapping("/active/not_free")
    fun getActiveNotFree(
        @PageableDefault pageable: Pageable
    ): Page<Raffle> {
        return raffleService.getActiveNotFreeRaffle(pageable)
    }

    @GetMapping("/active/free")
    fun getActiveFree(
        @PageableDefault pageable: Pageable
    ): Page<Raffle> {
        return raffleService.getActiveFreeRaffle(pageable)
    }

    @GetMapping("/active/not_free/detail/{raffleId}")
    fun getDetailNotFree(
        @PathVariable(value = "raffleId") raffleId: Long
    ): ResponseEntity<Raffle> {
        return raffleService.getDetailNotFree(raffleId)
    }

    @GetMapping("/active/free/detail/{raffleId}")
    fun getDetailFree(
        @PathVariable(value = "raffleId") raffleId: Long
    ): ResponseEntity<Raffle> {
        return raffleService.getDetailFree(raffleId)
    }


    @GetMapping("/all")
    fun getAll(): List<Raffle> {
        return raffleService.getAll()
    }
}