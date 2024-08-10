package com.van1164.lottoissofar.common.dto.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class PhoneNumberRequestDto(
    @JsonProperty("phone_number")
    val phoneNumber : String
)
