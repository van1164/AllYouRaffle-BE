package com.van1164.lottoissofar.common.dto.question_post.response

import com.van1164.lottoissofar.common.domain.QuestionComment
import com.van1164.lottoissofar.common.dto.question_post.ReadQuestionPostDto
import java.time.LocalDateTime


data class ReadQuestionPostDetailsResponse(
    val id: Long,
    val nickname: String,
    val userId: String,
    val createdDate: LocalDateTime,
    val title: String,
    val body: String,
    val isAdopted: Boolean,
    val commentList: MutableList<QuestionComment>
) {
    companion object {
        fun of(dto: ReadQuestionPostDto, commentList: MutableList<QuestionComment>): ReadQuestionPostDetailsResponse {
            return ReadQuestionPostDetailsResponse(
                id = dto.id,
                nickname = dto.nickname,
                userId = dto.userId,
                createdDate = dto.createdDate,
                title = dto.title,
                body = dto.body,
                isAdopted = dto.isAdopted,
                commentList = commentList
            )
        }
    }
}