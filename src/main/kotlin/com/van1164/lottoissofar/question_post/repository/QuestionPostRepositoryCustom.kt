package com.van1164.lottoissofar.question_post.repository

import com.van1164.lottoissofar.common.dto.question_post.ReadQuestionPostDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface QuestionPostRepositoryCustom {
    fun findAllPaged(pageable: Pageable): Page<ReadQuestionPostDto>
}