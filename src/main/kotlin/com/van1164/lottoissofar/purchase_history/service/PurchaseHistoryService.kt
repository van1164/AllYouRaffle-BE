package com.van1164.lottoissofar.purchase_history.service

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PurchaseHistoryService(
    private val purchaseHistoryJpaRepository: PurchaseHistoryJpaRepository
) {


    @Transactional
    fun getPurchaseList(user:User): List<PurchaseHistory> {
        return purchaseHistoryJpaRepository.findAllByUser(user)
    }
}