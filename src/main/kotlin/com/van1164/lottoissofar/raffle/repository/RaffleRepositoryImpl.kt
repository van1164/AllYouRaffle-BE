package com.van1164.lottoissofar.raffle.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QRaffle.raffle
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus.ACTIVE
import jakarta.persistence.EntityManager
import org.springframework.data.repository.query.Param

class RaffleRepositoryImpl(
    private val em: EntityManager
) :RaffleRepositoryCustom {
    private final val query = JPAQueryFactory(em);

    override fun findAllByStatusIsACTIVE(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(raffle.status.eq(ACTIVE))
            .fetch();
    }

    override fun findAllByStatusIsACTIVEAndFree(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            )
            .fetch();
    }

    override fun findAllByStatusIsACTIVEAndNotFree(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false)
            )
            .fetch();
    }

    override fun findByStatusIsACTIVEAndNotFree(@Param(value = "raffleId") raffleId: Long): Raffle? {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false),
                raffle.id.eq(raffleId)
            )
            .fetchOne();
    }

    override fun findByStatusIsACTIVEAndFree(@Param(value = "raffleId") raffleId: Long): Raffle? {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true),
                raffle.id.eq(raffleId)
            )
            .fetchOne();
    }
//    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true order by r.currentCount desc limit 5")

    override fun findAllByStatusIsACTIVEPopular(): List<Raffle> {
        return query
            .select(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            ).orderBy(raffle.currentCount.desc())
            .limit(5)
            .fetch()
    }

}