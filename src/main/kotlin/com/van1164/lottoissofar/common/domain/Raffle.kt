package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "raffle")
data class Raffle(

    @Column(name = "total_count")
    val totalCount: Int = 0,

    @Column(name = "current_count")
    var currentCount: Int = 0,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RaffleStatus,

    @Column(name = "completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    var completedDate: LocalDateTime? = null,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "item", nullable = false)
    var item: Item,

    @ManyToOne
    @JoinColumn(name = "user")
    var winner: User? = null,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_raffle_purchase",
        joinColumns = [JoinColumn(name = "raffle_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var purchaseUsers : MutableList<User> = mutableListOf()
) : BaseEntity()

enum class RaffleStatus {
    ACTIVE, INACTIVE, COMPLETED
}