package com.van1164.lottoissofar.question_post.service

import com.van1164.lottoissofar.common.domain.QuestionPost
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.question_post.request.CreateQuestionPostRequest
import com.van1164.lottoissofar.common.dto.question_post.response.ReadQuestionPostResponse
import com.van1164.lottoissofar.question_post.repository.QuestionPostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class QuestionPostService (
   private val questionPostRepository: QuestionPostRepository
) {
    fun create(user: User, createQuestionPostRequest: CreateQuestionPostRequest): QuestionPost {
        return questionPostRepository.save(createQuestionPostRequest.toDomain(user))
    }

    fun getAll(page: Int, size: Int): Page<ReadQuestionPostResponse> {
        val pageable = PageRequest.of(page, size)
        return questionPostRepository.findAllPaged(pageable).map{ ReadQuestionPostResponse.of(it) }
    }
}