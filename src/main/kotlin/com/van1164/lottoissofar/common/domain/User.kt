package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import lombok.ToString
import org.hibernate.annotations.ColumnDefault
import kotlin.random.Random

@Entity
@Table(name = "user")
data class User(
    @Column(name = "user_id", unique = true, nullable = false)
    var userId: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String = createRandomNickname(),

    @Column(name = "password")
    val password: String? = null,

    @Column(name= "phone_number", unique = true)
    var phoneNumber: String? = null,

    @Column(name = "profile_image_url")
    val profileImageUrl: String? = null,

    @Column(name = "tickets", nullable = false)
    @ColumnDefault("0")
    var tickets : Int = 0,

    @OneToOne(cascade = [CascadeType.ALL])
    var address: UserAddress? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role : Role = Role.USER,

    @JsonIgnore
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
    var purchaseHistoryList : MutableList<PurchaseHistory> = mutableListOf(),

    @Column(name = "fcm_token")
    @JsonIgnore
    var fcmToken: String? = null,

) : BaseEntity(){

    fun toDeletedUser():DeletedUser{
        return DeletedUser(
            userId = userId,
            email = email,
            name = name,
            nickname = nickname,
            password = password,
            phoneNumber = phoneNumber,
            profileImageUrl = profileImageUrl,
            tickets = tickets,
            address = address,
            role = role,
            formerId = id
        )
    }

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

enum class Role(val key: String, val title: String) {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자"),
    ADMIN("ROLE_ADMIN","관리자")
}