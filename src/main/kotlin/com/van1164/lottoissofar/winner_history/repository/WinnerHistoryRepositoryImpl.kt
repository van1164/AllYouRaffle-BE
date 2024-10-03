package com.van1164.lottoissofar.winner_history.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.domain.QItem.*
import com.van1164.lottoissofar.common.domain.QRaffle.*
import com.van1164.lottoissofar.common.domain.QReview.*
import com.van1164.lottoissofar.common.domain.QWinnerHistory.*
import com.van1164.lottoissofar.common.dto.winner_history.ReadWinnerHistoryDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils

class WinnerHistoryRepositoryImpl (
    em : EntityManager
) : WinnerHistoryRepositoryCustom {
    private val query = JPAQueryFactory(em)
    override fun findByUserId(userId: String, cursor: Long?, size: Int): Page<ReadWinnerHistoryDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        ReadWinnerHistoryDto::class.java,
                        winnerHistory.id,
                        winnerHistory.userId,
                        winnerHistory.raffleId,
                        raffle.item.name,
                        raffle.item.imageUrl,
                        raffle.completedDate,
                        winnerHistory.review.title,
                        winnerHistory.review.description,
                        winnerHistory.review.imageUrl,
                    )
            )
            .from(winnerHistory)
            .where(
                winnerHistory.userId.eq(userId),
                winnerHistory.id.lt(cursor)
            )
            .innerJoin(raffle).on(raffle.id.eq(winnerHistory.raffleId))
            .innerJoin(raffle.item, item)
            .leftJoin(winnerHistory.review, review)
            .orderBy(winnerHistory.id.desc())
            .limit(size.toLong())
            .fetch()

        val countQuery = {
            query
                .select(winnerHistory.count())
                .from(winnerHistory)
                .where(
                    winnerHistory.userId.eq(userId),
                    winnerHistory.id.lt(cursor)
                )
                .fetchOne()?: 0L
        }

        return PageableExecutionUtils.getPage(list, PageRequest.ofSize(size), countQuery)
    }
}