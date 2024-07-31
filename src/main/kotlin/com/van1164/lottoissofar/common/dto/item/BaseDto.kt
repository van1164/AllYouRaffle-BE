package com.van1164.lottoissofar.common.dto.item

import com.van1164.lottoissofar.common.domain.Item

interface BaseDto <T> {
    fun toDomain() : T
}