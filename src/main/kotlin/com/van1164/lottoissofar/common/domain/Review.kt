package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "review")
data class Review(
    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val user : User,

    @OneToOne(mappedBy = "review")
    val winnerHistory: WinnerHistory

    ) : BaseEntity() {
    override fun toString(): String {
        return "Review(title='$title', description='$description', imageUrl='$imageUrl')"
    }
}
