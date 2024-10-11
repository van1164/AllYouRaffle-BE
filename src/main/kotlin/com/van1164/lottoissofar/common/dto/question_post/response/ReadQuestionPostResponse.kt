package com.van1164.lottoissofar.common.dto.question_post.response

import com.van1164.lottoissofar.common.dto.question_post.ReadQuestionPostDto
import java.time.LocalDateTime

data class ReadQuestionPostResponse (
    val id : Long,
    val nickname : String,
    val userId : String,
    val createdDate : LocalDateTime,
    val body : String,
    val isAdopted : Boolean,
) {
    companion object {
        fun of(dto: ReadQuestionPostDto): ReadQuestionPostResponse {
            return ReadQuestionPostResponse(
                id = dto.id,
                nickname = dto.nickname,
                userId = dto.userId,
                createdDate = dto.createdDate,
                body = dto.body,
                isAdopted = dto.isAdopted
            )
        }
    }
}