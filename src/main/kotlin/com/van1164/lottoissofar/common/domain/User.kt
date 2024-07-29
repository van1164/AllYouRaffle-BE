package com.van1164.lottoissofar.common.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import kotlin.random.Random

@Entity
@Table(name = "user")
data class User(
    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "email")
    val email: String? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String = createRandomNickname(),

    @Column(name = "password")
    val password: String? = null
) : BaseEntity(){

    companion object{
        fun createRandomNickname(): String{
            val adjectives = listOf("빠른", "조용한", "영리한", "용감한", "재치있는", "강한", "행복한")
            val nouns = listOf("호랑이", "독수리", "사자", "표범", "상어", "매", "늑대")

            val adjective = adjectives.random()
            val noun = nouns.random()
            val number = Random.nextInt(100, 999)

            return "$adjective$noun$number"
        }
    }
}