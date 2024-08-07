package com.van1164.lottoissofar.purchase_history.repository

import com.van1164.lottoissofar.common.domain.PurchaseHistory
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PurchaseHistoryJpaRepository : JpaRepository<PurchaseHistory,Long> {

    fun existsDistinctByUserAndRaffle(user: User, raffle: Raffle) : Boolean

    fun findAllByUser(user:User) : List<PurchaseHistory>
}