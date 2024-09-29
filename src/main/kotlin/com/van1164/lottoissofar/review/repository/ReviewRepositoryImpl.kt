package com.van1164.lottoissofar.review.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QReview.review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils

class ReviewRepositoryImpl (
    em: EntityManager
) : ReviewRepositoryCustom {
    private val query = JPAQueryFactory(em)

    override fun findAllPaged(cursor: Long, size: Int): Page<ReadReviewDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        ReadReviewDto::class.java,
                        review
                    )
            )
            .from(review)
            .where(review.id.lt(cursor))
            .orderBy(review.id.desc())
            .limit(size.toLong())
            .fetch()

        val countQuery = {
            query
                .select(review.count())
                .from(review)
                .where(review.id.lt(cursor))
                .fetchOne()?: 0L
        }

        return PageableExecutionUtils.getPage(list, PageRequest.ofSize(size), countQuery)
    }

    override fun findAllPagedWithUser(user: User, cursor: Long, size: Int): Page<ReadReviewDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        ReadReviewDto::class.java,
                        review
                    )
            )
            .from(review)
            .where(
                review.user.id.eq(user.id),
                review.id.lt(cursor)
            )
            .orderBy(review.id.desc())
            .limit(size.toLong())
            .fetch()

        val countQuery = {
            query
                .select(review.count())
                .from(review)
                .where(
                    review.user.id.eq(user.id),
                    review.id.lt(cursor)
                )
                .fetchOne()?: 0L
        }

        return PageableExecutionUtils.getPage(list, PageRequest.ofSize(size), countQuery)
    }
}