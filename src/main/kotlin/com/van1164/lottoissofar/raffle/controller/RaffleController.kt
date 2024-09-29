package com.van1164.lottoissofar.raffle.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.raffle.PurchaseTicketDto
import com.van1164.lottoissofar.common.security.CustomUserDetails
import com.van1164.lottoissofar.raffle.service.RaffleService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
    ): List<Raffle> {
        val page = 0 // 페이지 번호 (0부터 시작)
        val size = 10 // 페이지당 아이템 개수
        val pageable: Pageable = PageRequest.of(page, size)
        return raffleService.getActiveFreeRaffle(pageable = pageable).toList()
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