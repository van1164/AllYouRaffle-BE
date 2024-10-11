package com.van1164.lottoissofar.question_post.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.QQuestionPost.*
import com.van1164.lottoissofar.common.dto.question_post.ReadQuestionPostDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.support.PageableExecutionUtils

class QuestionPostRepositoryImpl(
    em: EntityManager
) : QuestionPostRepositoryCustom {
    private val query = JPAQueryFactory(em)

    override fun findAllPaged(pageable: Pageable): Page<ReadQuestionPostDto> {
        val list = query.select(
            Projections
                .constructor(
                    ReadQuestionPostDto::class.java,
                    questionPost.id,
                    questionPost.nickname,
                    questionPost.userId,
                    questionPost.createdDate,
                    questionPost.title,
                    questionPost.body,
                    questionPost.isAdopted
                )
        )
            .from(questionPost)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val countQuery = {
            query
                .select(questionPost.count())
                .from(questionPost)
                .fetchOne()?: 0L
        }

        return PageableExecutionUtils.getPage(list, pageable, countQuery)
    }
}