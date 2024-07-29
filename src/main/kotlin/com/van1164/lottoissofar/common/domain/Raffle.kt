package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "raffle")
data class Raffle(

    @Column(name = "total_count")
    val totalCount: Int = 0,

    @Column(name = "current_count")
    val currentCount: Int = 0,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    val status: RaffleStatus,

    @Column(name = "completed_date")
    @Temporal(TemporalType.DATE)
    val completedDate: Date? = null,

    @ManyToOne
    @JoinColumn(name = "item", nullable = false)
    val item: Item,

    @ManyToOne
    @Column(name = "user", nullable = false)
    val user: User,
) : BaseEntity()

enum class RaffleStatus {
    ACTIVE, INACTIVE, COMPLETED
}