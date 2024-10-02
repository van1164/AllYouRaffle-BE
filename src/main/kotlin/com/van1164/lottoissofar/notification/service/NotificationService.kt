package com.van1164.lottoissofar.notification.service

import com.van1164.lottoissofar.common.domain.Notification
import com.van1164.lottoissofar.notification.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class NotificationService(private val repository: NotificationRepository) {

    @Transactional
    fun saveNotification(notification: Notification): Notification {
        return repository.save(notification)
    }
}