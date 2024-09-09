package com.van1164.lottoissofar.raffle.repository

import com.van1164.lottoissofar.common.domain.Raffle
import org.springframework.data.jpa.repository.JpaRepository

interface RaffleRepository : JpaRepository<Raffle,Long>, RaffleRepositoryCustom{

}