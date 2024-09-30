package com.van1164.lottoissofar.user.repository

import com.van1164.lottoissofar.common.domain.DeletedUser
import org.springframework.data.jpa.repository.JpaRepository

interface DeleteUserRepository : JpaRepository<DeletedUser, Long> {
    fun findByUserId(userId:String): List<DeletedUser>
}