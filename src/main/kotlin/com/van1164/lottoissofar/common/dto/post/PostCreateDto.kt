package com.van1164.lottoissofar.common.dto.post

import java.time.LocalDate


data class PostCreateDto(
    val redirectUrl : String? = null,
    val deadLine : LocalDate? = null,
)
