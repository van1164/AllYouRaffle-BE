package com.van1164.lottoissofar.purchase_history.controller

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.purchase_history.service.PurchaseHistoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/purchase_history")
class PurchaseHistoryController(
    private val purchaseHistoryService: PurchaseHistoryService
) {

    @GetMapping("")
    fun getPurchaseHistory(user : User): List<PurchaseHistory> {
        return purchaseHistoryService.getPurchaseList(user)
    }
}