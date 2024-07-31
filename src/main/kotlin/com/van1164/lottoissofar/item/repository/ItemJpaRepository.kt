package com.van1164.lottoissofar.item.repository

import com.van1164.lottoissofar.common.domain.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemJpaRepository : JpaRepository<Item, Long> {

    fun findByName(name: String): Item?
}