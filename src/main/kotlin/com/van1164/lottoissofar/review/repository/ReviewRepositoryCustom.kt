package com.van1164.lottoissofar.review.repository

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ReviewRepositoryCustom {
    fun findAllPaged(pageable: Pageable): Page<ReadReviewDto>
    fun findAllPagedWithUser(user: User, pageable: Pageable): Page<ReadReviewDto>
}