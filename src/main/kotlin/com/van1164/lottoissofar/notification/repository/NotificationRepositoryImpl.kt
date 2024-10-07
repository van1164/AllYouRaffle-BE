package com.van1164.lottoissofar.notification.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import com.van1164.lottoissofar.common.domain.Notification
import com.van1164.lottoissofar.common.domain.QNotification.notification
import com.van1164.lottoissofar.common.dto.notification.NotificationResponseDto
import com.van1164.lottoissofar.common.dto.winner_history.ReadWinnerHistoryDto
import jakarta.persistence.EntityManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository
import java.awt.print.Pageable

@Repository
class NotificationRepositoryImpl(
    em : EntityManager
) : NotificationRepositoryCustom {
    private val query = JPAQueryFactory(em)
    override fun findAllByUserIdWithPage(userId: String, cursor: Long, size: Int): Page<NotificationResponseDto> {
        val list = query
            .select(
                Projections
                    .constructor(
                        NotificationResponseDto::class.java,
                        notification.title,
                        notification.body,
                        notification.createdDate
                    )
            )
            .from(notification)
            .where(
                notification.userId.eq(userId),
                notification.id.lt(cursor)
            )
            .orderBy(notification.createdDate.desc())
            .limit(size.toLong())
            .fetch()

        println(list)
        println(list.count())
        val countQuery = {
            query
                .select(notification.count())
                .from(notification)
                .where(
                    notification.userId.eq(userId),
                    notification.id.lt(cursor)
                )
                .fetchOne()?: 0L
        }
        println(countQuery())

        return PageableExecutionUtils.getPage(list, PageRequest.ofSize(size), countQuery)
    }
}