package com.van1164.lottoissofar.common.dto.user

data class AppleSignupDto(
    val email: String,
    val name: String,
    val profileImageUrl: String?,
    val userNameAttributeNameValue: String,
    val userId : String
)
