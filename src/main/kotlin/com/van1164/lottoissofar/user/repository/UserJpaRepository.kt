package com.van1164.lottoissofar.user.repository

import com.van1164.lottoissofar.common.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User,Long> {
}