package com.van1164.lottoissofar.raffle.repository

import com.van1164.lottoissofar.common.domain.Raffle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param

interface RaffleRepositoryCustom {
    fun findAllByStatusIsACTIVE(pageable: Pageable): Page<Raffle>

    fun findAllByStatusIsACTIVEAndFree(pageable: Pageable): Page<Raffle>

    fun findAllByStatusIsACTIVEAndNotFree(pageable: Pageable): Page<Raffle>

    fun findByStatusIsACTIVEAndNotFree(@Param(value= "raffleId") raffleId : Long): Raffle?

    fun findByStatusIsACTIVEAndFree(@Param(value = "raffleId") raffleId : Long): Raffle?

//    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true order by r.currentCount desc limit 5")
    fun findAllByStatusIsACTIVEPopular(): List<Raffle>
}