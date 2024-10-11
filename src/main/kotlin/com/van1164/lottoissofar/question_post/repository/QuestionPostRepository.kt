package com.van1164.lottoissofar.question_post.repository

import com.van1164.lottoissofar.common.domain.QuestionPost
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionPostRepository: JpaRepository<QuestionPost, Long>, QuestionPostRepositoryCustom{
}