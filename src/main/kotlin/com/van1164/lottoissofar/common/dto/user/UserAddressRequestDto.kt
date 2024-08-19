package com.van1164.lottoissofar.common.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.van1164.lottoissofar.common.domain.UserAddress
import jakarta.persistence.Column

data class UserAddressRequestDto(
    val address: String, // 주소

    val addressEnglish: String, // 주소

    val bname : String,

    val jibunAddress: String, // 지번

    val jibunAddressEnglish: String, // 지번 영어

    val roadAddress : String,

    val sido: String, // 주/도명

    val sigungu: String, // 시 구

    val detail: String, // 몇 동 몇호

    val postalCode: String, // 우편번호

    val country: String, // 국가명

    val isDefault: Boolean, // 기본 주소 여부
) {
    fun toDomain(): UserAddress {
        return UserAddress(
            address = this.address,
            addressEnglish = this.addressEnglish,
            bname = this.bname,
            jibunAddress = this.jibunAddress,
            jibunAddressEnglish = this.jibunAddressEnglish,
            roadAddress=this.roadAddress,
            sido = this.sido,
            sigungu = this.sigungu,
            detail = this.detail,
            postalCode = this.postalCode,
            country = this.country,
            isDefault = this.isDefault
        )
    }
}
