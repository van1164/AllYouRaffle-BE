package com.van1164.lottoissofar.common.dto.question_post

import java.time.LocalDateTime


data class ReadQuestionPostDto (
    val id : Long,
    val nickname : String,
    val userId : String,
    val createdDate : LocalDateTime,
    val body : String,
    val isAdopted : Boolean,
)