package com.van1164.lottoissofar.common.dto.question_comment.request

import com.van1164.lottoissofar.common.domain.QuestionComment
import com.van1164.lottoissofar.common.domain.User

data class CreateQuestionCommentRequest (
    val userId: String,
    val nickname: String,
    val questionPostId: Long,
    val body: String,
    val isAdopted: Boolean
) {
    fun toDomain(user: User): QuestionComment {
        return QuestionComment(
            userId = user.userId,
            nickname = user.nickname,
            questionPostId = questionPostId,
            body = body,
            isAdopted = isAdopted
        )
    }
}