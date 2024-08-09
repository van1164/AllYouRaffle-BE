package com.van1164.lottoissofar.raffle.repository

import com.van1164.lottoissofar.common.domain.Raffle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface RaffleJpaRepository : JpaRepository<Raffle,Long> {

    @Query("select r from Raffle r where r.status = 'ACTIVE'")
    fun findAllByStatusIsACTIVE(): List<Raffle>
}