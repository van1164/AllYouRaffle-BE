package com.van1164.lottoissofar.user.repository

import com.van1164.lottoissofar.common.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserJpaRepository : JpaRepository<User, Long> {

    fun findUserByUserId(userId: String) : User?
}