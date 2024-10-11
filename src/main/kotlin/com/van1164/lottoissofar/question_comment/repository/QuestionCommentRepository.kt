package com.van1164.lottoissofar.question_comment.repository

import com.van1164.lottoissofar.common.domain.QuestionComment
import org.springframework.data.jpa.repository.JpaRepository

interface QuestionCommentRepository: JpaRepository<QuestionComment, Long> {
}