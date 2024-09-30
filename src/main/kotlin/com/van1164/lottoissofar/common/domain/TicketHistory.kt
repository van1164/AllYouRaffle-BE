package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "ticket_history")
data class TicketHistory(

    @Column(name = "user_id",nullable = false)
    val userId: Long,

    @Column(name = "ticket_count",nullable = false)
    val ticketCount : Int

) : BaseEntity() {
    override fun toString(): String {
        return "TicketHistory(userId=${userId}, ticketCount=${ticketCount})"
    }
}
