package com.van1164.lottoissofar.purchase_history.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QItem.*
import com.van1164.lottoissofar.common.domain.QPurchaseHistory.purchaseHistory
import com.van1164.lottoissofar.common.domain.QRaffle
import com.van1164.lottoissofar.common.domain.QRaffle.raffle
import com.van1164.lottoissofar.common.domain.QUser
import com.van1164.lottoissofar.common.domain.QUserAddress.userAddress
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.purchase_history.PaidRaffleDto
import jakarta.persistence.EntityManager

class PurchaseHistoryRepositoryImpl(
    private val em: EntityManager
) : PurchaseHistoryRepositoryCustom {
    private final val query = JPAQueryFactory(em);

    override fun findPaidRaffles(user: User, offset: Long, limit: Long): List<PaidRaffleDto> {
        val qUser = QUser.user
        val qRaffle = QRaffle.raffle
        val isWinner = Expressions.booleanTemplate(
            "coalesce({0} = {1}, false)",
            qRaffle.winner.userId,
            user.userId
        )
        val result = query
            .select(
                Projections.constructor(
                    PaidRaffleDto::class.java,
                    qRaffle,
                    purchaseHistory.count(),
                    isWinner
                )
            )
            .from(purchaseHistory)
            .join(purchaseHistory.raffle, qRaffle)
            .join(qRaffle.item, item).fetchJoin()
            .leftJoin(qRaffle.winner, qUser).fetchJoin()
            .leftJoin(qUser.address, userAddress).fetchJoin()
            .where(purchaseHistory.user.userId.eq(user.userId))
            .orderBy(purchaseHistory.createdDate.desc())
            .groupBy(qRaffle)
            .distinct()
            .offset(offset)
            .limit(limit)
            .fetch()
        return result

    }

}