package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "winner_history")
data class WinnerHistory(
    @Column(name = "userId")
    val userId: String,

    @Column(name = "raffleId")
    val raffleId: Long,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: WinnerHistoryStatus = WinnerHistoryStatus.BEFORE,

    @OneToOne
    var review: Review? = null,

    ):BaseEntity()

enum class WinnerHistoryStatus {
    BEFORE, DELIVERY, COMPLETED
}