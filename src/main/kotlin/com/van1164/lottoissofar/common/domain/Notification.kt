package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "notifications")
data class Notification(
    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "title", nullable = false)
    val title: String,

    @Column(name = "body", nullable = false)
    val body: String,

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "code", nullable = false)
    @JsonIgnore
    val code: NotificationType = NotificationType.NORMAL,

    @Column(name = "is_read")
    val isRead: Boolean = false,


    ) : BaseEntity() {}

enum class NotificationType {
    WINNER, NORMAL
}