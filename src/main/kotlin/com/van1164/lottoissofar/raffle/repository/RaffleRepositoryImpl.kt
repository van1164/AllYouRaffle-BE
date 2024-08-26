package com.van1164.lottoissofar.raffle.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QItem
import com.van1164.lottoissofar.common.domain.QItem.*
import com.van1164.lottoissofar.common.domain.QRaffle.raffle
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus.ACTIVE
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.support.PageableUtils
import org.springframework.data.repository.query.Param
import org.springframework.data.support.PageableExecutionUtils
import java.util.function.LongSupplier

class RaffleRepositoryImpl(
    private val em: EntityManager
) :RaffleRepositoryCustom {
    private final val query = JPAQueryFactory(em);

    override fun findAllByStatusIsACTIVE(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(raffle.status.eq(ACTIVE))
            .innerJoin(raffle.item, item).fetchJoin()
            .orderBy(raffle.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalSupplier = {
            query
                .select(raffle.count())
                .from(raffle)
                .where(raffle.status.eq(ACTIVE))
                .fetchOne()!!
        }

        return PageableExecutionUtils.getPage(list, pageable, totalSupplier)
    }

    override fun findAllByStatusIsACTIVEAndFree(): List<Raffle> {
        return query

            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            )
            .innerJoin(raffle.item, item).fetchJoin()
            .orderBy(raffle.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalSupplier = {
            query
                .select(raffle.count())
                .from(raffle)
                .where(
                    raffle.status.eq(ACTIVE),
                    raffle.isFree.eq(true)
                )
                .fetchOne()!!
        }

        return PageableExecutionUtils.getPage(list, pageable, totalSupplier)
    }

    override fun findAllByStatusIsACTIVEAndNotFree(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false)
            )
            .innerJoin(raffle.item, item).fetchJoin()
            .orderBy(raffle.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val totalSupplier = {
            query
                .select(raffle.count())
                .from(raffle)
                .where(
                    raffle.status.eq(ACTIVE),
                    raffle.isFree.eq(false)
                )
                .fetchOne()!!
        }

        return PageableExecutionUtils.getPage(list, pageable, totalSupplier)
    }

    override fun findByStatusIsACTIVEAndNotFree(@Param(value = "raffleId") raffleId: Long): Raffle? {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false),
                raffle.id.eq(raffleId)
            )
            .innerJoin(raffle.item, item).fetchJoin()
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
            .innerJoin(raffle.item, item).fetchJoin()
            .fetchOne();
    }
//    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true order by r.currentCount desc limit 5")

    override fun findAllByStatusIsACTIVEPopular(): List<Raffle> {
        return query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            ).orderBy(raffle.currentCount.desc())
            .limit(5)
            .fetch()
    }

}