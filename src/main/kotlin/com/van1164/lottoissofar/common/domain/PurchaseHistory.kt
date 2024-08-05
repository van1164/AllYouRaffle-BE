package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "purchase_history")
data class PurchaseHistory(

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "raffle_id")
    val raffle: Raffle

) : BaseEntity() {
    override fun toString(): String {
        return "PurchaseHistory(userId=${user.id}, raffleId=${raffle.id})"
    }
}
