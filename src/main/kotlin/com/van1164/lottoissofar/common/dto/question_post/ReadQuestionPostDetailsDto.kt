package com.van1164.lottoissofar.common.dto.question_post

import com.van1164.lottoissofar.common.domain.QuestionComment
import kotlinx.datetime.LocalDateTime

data class ReadQuestionPostDetailsDto (
    val id : Long,
    val nickName : String,
    val userId : String,
    val createdDate : LocalDateTime,
    val body : String,
    val isAdopted : Boolean,
    val commentList : List<QuestionComment>
)