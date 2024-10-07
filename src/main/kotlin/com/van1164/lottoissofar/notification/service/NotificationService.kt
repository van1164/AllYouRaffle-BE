package com.van1164.lottoissofar.notification.service

import com.van1164.lottoissofar.common.domain.Notification
import com.van1164.lottoissofar.common.dto.notification.NotificationResponseDto
import com.van1164.lottoissofar.notification.repository.NotificationRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class NotificationService(private val repository: NotificationRepository) {

    fun getNotificationsByUserId(userId: String,cursor: Long, size : Int): Page<NotificationResponseDto> {
        return repository.findAllByUserIdWithPage(userId,cursor,size)
    }

    @Transactional
    fun saveNotification(notification: Notification): Notification {
        return repository.save(notification)
    }

    @Transactional
    fun saveNotifications(notifications: List<Notification>): List<Notification> {
        return repository.saveAll(notifications)
    }
}