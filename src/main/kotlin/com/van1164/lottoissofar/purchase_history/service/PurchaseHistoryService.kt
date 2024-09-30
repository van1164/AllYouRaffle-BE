package com.van1164.lottoissofar.purchase_history.service

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.purchase_history.PaidRaffleDto
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PurchaseHistoryService(
    private val purchaseHistoryRepository: PurchaseHistoryRepository
) {


    @Transactional
    fun getPurchaseList(user: User): List<PurchaseHistory> {
        return purchaseHistoryRepository.findAllByUser(user)
    }

    @Transactional
    fun getPaidRaffles(user: User, offset: Long, size: Long): List<PaidRaffleDto> {
        return purchaseHistoryRepository.findPaidRaffles(user, offset, size)
    }
}