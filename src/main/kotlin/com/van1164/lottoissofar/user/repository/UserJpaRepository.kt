package com.van1164.lottoissofar.user.repository

import com.querydsl.core.annotations.QueryType
import com.van1164.lottoissofar.common.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserJpaRepository : JpaRepository<User, Long> {

    fun existsByPhoneNumberIs(phoneNumber : String) : Boolean
    @Query("select u from User u where u.userId = :userId and  u.deletedDate is null")
    fun findUserByUserId(userId: String) : User?
}