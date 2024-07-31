package com.van1164.lottoissofar.common.domain

import jakarta.persistence.*

@Entity
@Table(name = "user_addresses")
data class UserAddress(


    @OneToOne(mappedBy = "address")
    var user: User?, // 사용자 ID

    @Column(nullable = false)
    val street: String, // 거리명

    @Column(nullable = false)
    val city: String, // 도시명

    @Column(nullable = false)
    val state: String, // 주/도명

    @Column(nullable = false)
    val postalCode: String, // 우편번호

    @Column(nullable = false)
    val country: String, // 국가명

    @Column
    val isDefault: Boolean = false // 기본 주소 여부
) : BaseEntity() {
    override fun toString(): String {
        return "UserAddress(street='$street', city='$city', state='$state', postalCode='$postalCode', country='$country', isDefault=$isDefault)"
    }
}