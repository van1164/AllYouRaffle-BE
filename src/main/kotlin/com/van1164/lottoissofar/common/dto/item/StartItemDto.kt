package com.van1164.lottoissofar.common.dto.item

import com.fasterxml.jackson.annotation.JsonProperty

data class StartItemDto(
    @JsonProperty("total_count")
    val totalCount : Int?,
    @JsonProperty("ticket_price")
    val ticketPrice : Int?
)
