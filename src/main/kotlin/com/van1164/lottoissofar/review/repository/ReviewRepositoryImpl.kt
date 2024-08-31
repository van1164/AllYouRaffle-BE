package com.van1164.lottoissofar.review.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.StringTemplate
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QReview.*
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class ReviewRepositoryImpl (
    private val em: EntityManager
) {
    private final val query = JPAQueryFactory(em)

    fun findAllPaged(pageable: Pageable): Page<ReadReviewDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        ReadReviewDto::class.java,
                        review
                    )
            )
            .from(review)
            .orderBy(review.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = {
            query
                .select(review.count())
                .from(review)
                .fetchOne()!!
        }

        return PageableExecutionUtils.getPage(list, pageable, countQuery)
    }

    fun findAllPagedWithUser(user: User, pageable: Pageable): Page<ReadReviewDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        ReadReviewDto::class.java,
                        review
                    )
            )
            .from(review)
            .where(review.user.id.eq(user.id))
            .orderBy(review.createdDate.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = {
            query
                .select(review.count())
                .from(review)
                .where(review.user.id.eq(user.id))
                .fetchOne()!!
        }

        return PageableExecutionUtils.getPage(list, pageable, countQuery)
    }
}