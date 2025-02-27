package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString
import java.time.LocalDateTime

@Entity
@Table(name = "raffle")
data class Raffle(

    @Column(name = "total_count")
    val totalCount: Int = 0,

    @Column(name = "current_count")
    var currentCount: Int = 0,

    @Column(name = "ticket_price")
    var ticketPrice : Int = 1000,

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RaffleStatus,

    @Column(name = "completed_date")
    @Temporal(TemporalType.TIMESTAMP)
    var completedDate: LocalDateTime? = null,

    @Column(name = "is_free")
    val isFree: Boolean = false,

    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "item", nullable = false)
    var item: Item,

    @ManyToOne
    @JoinColumn(name = "user")
    var winner: User? = null,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "raffle", cascade = [CascadeType.ALL])
    var purchaseHistoryList : MutableList<PurchaseHistory> = mutableListOf()
) : BaseEntity()

enum class RaffleStatus {
    ACTIVE, INACTIVE, COMPLETED
}