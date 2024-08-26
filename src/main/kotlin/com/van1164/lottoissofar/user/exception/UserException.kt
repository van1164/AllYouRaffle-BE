package com.van1164.lottoissofar.user.exception

class AlreadySavedPhoneNumber(override val message: String = "이미 등록된 전화번호 입니다.") : RuntimeException()