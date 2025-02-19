package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString

@Entity
@Table(name = "item")
data class Item(
    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "category", nullable = false)
    val category: Int,

    @Column(name = "image_url")
    val imageUrl: String? = null,

    @Column(name = "description")
    val description: String? = null,

    @Column(name = "default_total_count")
    val defaultTotalCount : Int = 0,

    @Column(name = "possible_raffle")
    var possibleRaffle : Boolean = true,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", orphanRemoval = true, cascade = [CascadeType.ALL])
    val raffleList : MutableList<Raffle> = mutableListOf(),

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", orphanRemoval = true, cascade = [CascadeType.ALL])
    val imageList : MutableList<ItemDescriptionImage> = mutableListOf()

) : BaseEntity() {
    override fun toString(): String {
        return "Item(name='$name', category=$category, imageUrl=$imageUrl, description=$description, defaultTotalCount=$defaultTotalCount, possibleRaffle=$possibleRaffle, imageList=$imageList)"
    }
}