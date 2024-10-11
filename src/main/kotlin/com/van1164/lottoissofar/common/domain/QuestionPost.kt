package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "question_post")
data class QuestionPost (
    @Column(name = "nickname", nullable = false)
    val nickname: String,
    @Column(name = "user_id", nullable = false)
    val userId: String,
    @Column(name = "body", nullable = false)
    val body: String,
    @Column(name = "is_adopted", nullable = false)
    val isAdopted: Boolean,
    @OneToMany(fetch = FetchType.LAZY)
    val commentList: MutableList<QuestionComment> = mutableListOf()
): BaseEntity()