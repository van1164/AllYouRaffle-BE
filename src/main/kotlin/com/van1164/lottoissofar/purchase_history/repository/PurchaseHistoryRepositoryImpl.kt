package com.van1164.lottoissofar.purchase_history.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QItem
import com.van1164.lottoissofar.common.domain.QItem.*
import com.van1164.lottoissofar.common.domain.QPurchaseHistory
import com.van1164.lottoissofar.common.domain.QPurchaseHistory.purchaseHistory
import com.van1164.lottoissofar.common.domain.QRaffle.raffle
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.purchase_history.PaidRaffleDto
import jakarta.persistence.EntityManager

class PurchaseHistoryRepositoryImpl(
    private val em: EntityManager
) {
    private final val query = JPAQueryFactory(em);

    fun findPaidRaffles(user: User): List<PaidRaffleDto> {
        return query
            .select(
                Projections.constructor(
                    PaidRaffleDto::class.java,
                    purchaseHistory.raffle,
                    purchaseHistory.count()
                )
            )
            .from(purchaseHistory)
            .innerJoin(purchaseHistory.raffle, raffle)
            .innerJoin(purchaseHistory.raffle.item, item).fetchJoin()
            .where(purchaseHistory.user.eq(user))
            .groupBy(purchaseHistory.raffle)
            .fetch()
    }
}