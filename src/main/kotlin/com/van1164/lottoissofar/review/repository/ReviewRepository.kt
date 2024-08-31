package com.van1164.lottoissofar.review.repository

import com.van1164.lottoissofar.common.domain.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long>, ReviewRepositoryCustom{
}