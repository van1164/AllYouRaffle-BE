package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*


@Entity
@Table(name = "item_description_image")
data class ItemDescriptionImage(
    @Column(name = "image_url", nullable = false)
    val imageUrl: String,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val item : Item

): BaseEntity()
