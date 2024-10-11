package com.van1164.lottoissofar.question_comment.service

import com.van1164.lottoissofar.common.domain.QuestionComment
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.question_comment.request.CreateQuestionCommentRequest
import com.van1164.lottoissofar.question_comment.repository.QuestionCommentRepository
import org.springframework.stereotype.Service

@Service
class QuestionCommentService(
    private val questionCommentRepository: QuestionCommentRepository
) {
    fun create(user: User, createQuestionCommentRequest: CreateQuestionCommentRequest): QuestionComment {
        return questionCommentRepository.save(createQuestionCommentRequest.toDomain(user))
    }
}