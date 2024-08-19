package com.van1164.lottoissofar.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "user_addresses")
data class UserAddress(

    @Column(nullable = false)
    val address: String, // 주소

    @Column(nullable = false)
    val addressEnglish: String, // 주소

    @Column(nullable = false)
    val bname : String,

    @Column(nullable = false)
    val jibunAddress: String, // 지번

    @Column(nullable = false)
    val jibunAddressEnglish: String, // 지번 영어

    @Column(nullable = false)
    val roadAddress : String,

    @Column(nullable = false)
    val sido: String, // 주/도명

    @Column(nullable = false)
    val sigungu: String, // 시 구

    @Column(nullable = false)
    val detail: String, // 몇 동 몇호

    @Column(nullable = false)
    val postalCode: String, // 우편번호

    @Column(nullable = false)
    val country: String, // 국가명

    @Column
    val isDefault: Boolean = false, // 기본 주소 여부

    @JsonIgnore
    @OneToOne(mappedBy = "address")
    var user: User? = null, // 사용자 IDs


) : BaseEntity() {
    override fun toString(): String {
        return "UserAddress(address='$address', addressEnglish='$addressEnglish', bname='$bname', jibunAddress='$jibunAddress', jibunAddressEnglish='$jibunAddressEnglish', roadAddress='$roadAddress', sido='$sido', sigungu='$sigungu', detail='$detail', postalCode='$postalCode', country='$country', isDefault=$isDefault)"
    }

}