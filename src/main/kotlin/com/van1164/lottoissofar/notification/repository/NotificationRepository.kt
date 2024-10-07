package com.van1164.lottoissofar.notification.repository

import com.van1164.lottoissofar.common.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long>,NotificationRepositoryCustom {
}