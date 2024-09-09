package com.van1164.lottoissofar.common.dto.user

data class MobileUserLoginDto(
    val registrationId: String,
    val email: String,
    val name: String,
    val profileImageUrl: String?,
    val userNameAttributeNameValue: String
)
