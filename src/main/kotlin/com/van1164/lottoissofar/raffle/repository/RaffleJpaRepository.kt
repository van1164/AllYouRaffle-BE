package com.van1164.lottoissofar.raffle.repository

import com.van1164.lottoissofar.common.domain.Raffle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface RaffleJpaRepository : JpaRepository<Raffle,Long> {

    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true order by r.currentCount desc limit 5")
    fun findAllByStatusIsACTIVEPopular(): List<Raffle>


    @Query("select r from Raffle r where r.status = 'ACTIVE'")
    fun findAllByStatusIsACTIVE(): List<Raffle>


    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true")
    fun findAllByStatusIsACTIVEAndFree(): List<Raffle>

    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = false")
    fun findAllByStatusIsACTIVEAndNotFree(): List<Raffle>

    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = false and r.id = :raffleId")
    fun findByStatusIsACTIVEAndNotFree(@Param(value= "raffleId") raffleId : Long): Raffle?

    @Query("select r from Raffle r where r.status = 'ACTIVE' and r.isFree = true and r.id = :raffleId")
    fun findByStatusIsACTIVEAndFree(@Param(value = "raffleId") raffleId : Long): Raffle?
}