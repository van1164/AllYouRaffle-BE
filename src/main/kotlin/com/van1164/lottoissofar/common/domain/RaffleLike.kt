package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "raffle_like")
data class RaffleLike(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    val item: Item
) : BaseEntity()