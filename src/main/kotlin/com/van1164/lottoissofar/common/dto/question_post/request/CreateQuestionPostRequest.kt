package com.van1164.lottoissofar.common.dto.question_post.request

import com.van1164.lottoissofar.common.domain.QuestionPost
import com.van1164.lottoissofar.common.domain.User

data class CreateQuestionPostRequest (
    val userId : Long,
    val nickname : String,
    val body : String,
    val isAdopted : Boolean,
) {
    fun toDomain(user: User): QuestionPost {
        return QuestionPost(
            userId = user.userId,
            nickname = user.nickname,
            body = body,
            isAdopted = isAdopted
        )
    }
}