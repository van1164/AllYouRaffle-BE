package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "post")
data class Post(
    @Column(name = "image_url", nullable = false)
    val imageUrl : String,

    @Column(name = "dead_line")
    @Temporal(TemporalType.DATE)
    val deadLine : LocalDate? = null,

    @Column(name = "redirect_url")
    val redirectUrl : String? = null

):BaseEntity()
