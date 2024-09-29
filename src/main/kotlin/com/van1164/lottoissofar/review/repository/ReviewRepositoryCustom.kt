package com.van1164.lottoissofar.review.repository

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReviewRepositoryCustom {
    fun findAllPaged(cursor: Long, size: Int): Page<ReadReviewDto>
    fun findAllPagedWithUser(user: User, cursor: Long, size: Int): Page<ReadReviewDto>
}