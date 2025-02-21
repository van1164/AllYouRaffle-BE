package com.van1164.lottoissofar.question_comment.service

import com.van1164.lottoissofar.common.domain.QuestionComment
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.question_comment.request.CreateQuestionCommentRequest
import com.van1164.lottoissofar.common.dto.question_comment.request.UpdateQuestionCommentRequest
import com.van1164.lottoissofar.common.exception.ErrorCode
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.question_comment.repository.QuestionCommentRepository
import com.van1164.lottoissofar.question_post.repository.QuestionPostRepository
import org.springframework.stereotype.Service

@Service
class QuestionCommentService(
    private val questionPostRepository: QuestionPostRepository,
    private val questionCommentRepository: QuestionCommentRepository
) {
    fun create(user: User, request: CreateQuestionCommentRequest): QuestionComment {
        return questionCommentRepository.save(request.toDomain(user))
    }

    fun update(id: Long, request: UpdateQuestionCommentRequest): QuestionComment {
        val comment = questionCommentRepository.findById(id)
            .orElseThrow { GlobalExceptions.NotFoundException(ErrorCode.NOT_FOUND) }

        comment.body = request.body
        return questionCommentRepository.save(comment)
    }
}