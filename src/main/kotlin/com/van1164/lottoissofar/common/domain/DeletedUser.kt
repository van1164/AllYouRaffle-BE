package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import kotlin.random.Random

@Entity
@Table(name = "deleted_users")
data class DeletedUser(
    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "formerId", nullable = false)
    val formerId: Long,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Column(name = "password")
    val password: String? = null,

    @Column(name= "phone_number")
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

) : BaseEntity(){

}