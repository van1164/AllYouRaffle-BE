package com.van1164.lottoissofar.common.dto.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.van1164.lottoissofar.common.domain.UserAddress
import jakarta.persistence.Column

data class UserAddressRequestDto(
    val street: String, // 거리명

    val city: String, // 도시명

    val state: String, // 주/도명

    val postalCode: String, // 우편번호

    val country: String, // 국가명

    @JsonProperty("is_default")
    val isDefault: Boolean, // 기본 주소 여부
) {
    fun toDomain(): UserAddress {
        return UserAddress(
            street = this.street,
            city = this.city,
            state = this.state,
            postalCode = this.postalCode,
            country = this.country,
            isDefault = this.isDefault
        )
    }
}
