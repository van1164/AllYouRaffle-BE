package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "question_comment")
data class QuestionComment (
    @Column(name = "user_id")
    val userId: String,
    @Column(name = "nickname")
    val nickname: String,
    @Column(name = "question_post_id")
    val questionPostId: Long,
    @Column(name = "body")
    val body: String,
    @Column(name = "is_adopted")
    val isAdopted: Boolean
): BaseEntity()