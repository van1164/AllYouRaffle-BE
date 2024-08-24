package com.van1164.lottoissofar.raffle.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QRaffle.raffle
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus.ACTIVE
import jakarta.persistence.EntityManager
import org.springframework.data.repository.query.Param

class RaffleRepositoryImpl(
    private val em: EntityManager
) {
    private final val query = JPAQueryFactory(em);

    fun findAllByStatusIsACTIVE(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(raffle.status.eq(ACTIVE))
            .fetch();
    }

    fun findAllByStatusIsACTIVEAndFree(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            )
            .fetch();
    }

    fun findAllByStatusIsACTIVEAndNotFree(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false)
            )
            .fetch();
    }

    fun findByStatusIsACTIVEAndNotFree(@Param(value = "raffleId") raffleId: Long): Raffle? {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false),
                raffle.id.eq(raffleId)
            )
            .fetchOne();
    }

    fun findByStatusIsACTIVEAndFree(@Param(value = "raffleId") raffleId: Long): Raffle? {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true),
                raffle.id.eq(raffleId)
            )
            .fetchOne();
    }
}