package com.van1164.lottoissofar.notification.repository

import com.van1164.lottoissofar.common.domain.Notification
import com.van1164.lottoissofar.common.dto.notification.NotificationResponseDto
import org.springframework.data.domain.Page
import java.awt.print.Pageable

interface NotificationRepositoryCustom {

    fun findAllByUserIdWithPage(userId: String, cursor: Long, size: Int): Page<NotificationResponseDto>
}