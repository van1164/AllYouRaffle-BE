package com.van1164.lottoissofar.raffle.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.raffle.PurchaseTicketDto
import com.van1164.lottoissofar.common.security.CustomUserDetails
import com.van1164.lottoissofar.raffle.service.RaffleService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/raffle")
class RaffleController(
    private val raffleService: RaffleService
) {
    @Deprecated("티켓으로 대체할 예정")
    @PostMapping("/purchase/{raffleId}")
    fun purchase(
        @PathVariable(value = "raffleId") raffleId: Long,
        @Parameter(hidden = true)
        user: User
    ): ResponseEntity<PurchaseHistory> {
        return raffleService.purchaseRaffle(raffleId, user)
    }

    @PostMapping("/purchase_ticket/{raffleId}")
    fun purchaseTicket(
        @PathVariable(value = "raffleId") raffleId: Long,
        @RequestBody purchaseTicketDto: PurchaseTicketDto,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<MutableList<PurchaseHistory>> {
        return raffleService.purchaseRaffleWithTicket(raffleId, purchaseTicketDto.ticketCount, user.id)
    }

    @PostMapping("/purchase_ticket_one/{raffleId}")
    fun purchaseTicketOne(
        @PathVariable(value = "raffleId") raffleId: Long,
        @AuthenticationPrincipal user: CustomUserDetails
    ): ResponseEntity<MutableList<PurchaseHistory>> {
        return raffleService.purchaseRaffleOneTicket(raffleId, user.id)
    }




    @GetMapping("/active/popular")
    fun getActivePopular(): List<Raffle> {
        return raffleService.getActivePopular()
    }

    @GetMapping("/active")
    fun getActive(): List<Raffle> {
        return raffleService.getActive()
    }

    @GetMapping("/active/not_free")
    fun getActiveNotFree(): List<Raffle> {
        return raffleService.getActiveNotFreeRaffle()
    }

    @GetMapping("/active/free")
    fun getActiveFree(): List<Raffle> {
        return raffleService.getActiveFreeRaffle()
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