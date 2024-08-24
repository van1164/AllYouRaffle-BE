package com.van1164.lottoissofar.common.dto.purchase_history

import com.van1164.lottoissofar.common.domain.Raffle

data class PaidRaffleDto (
    val raffle: Raffle,
    val count: Long
)