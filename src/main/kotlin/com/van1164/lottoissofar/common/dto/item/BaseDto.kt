package com.van1164.lottoissofar.common.dto.item

interface BaseDto <T> {
    fun toDomain() : T
}