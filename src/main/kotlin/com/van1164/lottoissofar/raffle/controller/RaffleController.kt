package com.van1164.lottoissofar.raffle.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.security.CustomUserDetails
import com.van1164.lottoissofar.raffle.service.RaffleService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
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
        @PathVariable(value = "raffleId") raffleId : Long,
        @AuthenticationPrincipal userDetails : CustomUserDetails
//        @PathVariable(value = "userId") userId : Long
    ): ResponseEntity<PurchaseHistory> {
        val userId = userDetails.id
        return raffleService.purchaseRaffle(raffleId,userId) //TODO: user에 대한 추가 필요
    }

}