package com.van1164.lottoissofar.raffle.repository

import com.querydsl.jpa.impl.JPAQueryFactory
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
) {
    private final val query = JPAQueryFactory(em);

    fun findAllByStatusIsACTIVE(pageable: Pageable): Page<Raffle> {
        val list = query
            .selectFrom(raffle)
            .where(raffle.status.eq(ACTIVE))
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

    fun findAllByStatusIsACTIVEAndFree(pageable: Pageable): Page<Raffle> {
        val list = query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(true)
            )
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

    fun findAllByStatusIsACTIVEAndNotFree(pageable: Pageable): Page<Raffle> {
        val list = query
            .selectFrom(raffle)
            .where(
                raffle.status.eq(ACTIVE),
                raffle.isFree.eq(false)
            )
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