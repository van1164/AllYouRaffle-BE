package com.van1164.lottoissofar.common.response

data class CursorPage<T>(
    val data: List<T>,
    val nextCursor: Long?,
    val hasNext: Boolean
)