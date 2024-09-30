package com.van1164.lottoissofar.purchase_history.repository

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.purchase_history.PaidRaffleDto

interface PurchaseHistoryRepositoryCustom {
    fun findPaidRaffles(user: User, offset: Long, limit: Long): List<PaidRaffleDto>
}