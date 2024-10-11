package com.van1164.lottoissofar.question_post.repository

import com.van1164.lottoissofar.common.dto.question_post.ReadQuestionPostDto
import com.van1164.lottoissofar.common.dto.question_post.response.ReadQuestionPostDetailsResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionPostRepositoryCustom {
    fun findAllPaged(pageable: Pageable): Page<ReadQuestionPostDto>
    fun findDetails(id: Long): ReadQuestionPostDetailsResponse
}