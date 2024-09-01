package com.van1164.lottoissofar.review.service

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.CreateReviewDto
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import com.van1164.lottoissofar.review.repository.ReviewRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService (
    private val reviewRepository: ReviewRepository
){
    fun createNewReview(user: User, createReviewDto: CreateReviewDto): Review{
        return reviewRepository.save(createReviewDto.toDomain(user))
    }

    fun getAll(cursor: Long, pageable: Pageable): Page<ReadReviewDto>{
        return reviewRepository.findAllPaged(cursor, pageable)
    }

    fun getAllWithUser(user: User, cursor: Long, pageable: Pageable): Page<ReadReviewDto> {
        return reviewRepository.findAllPagedWithUser(user, cursor, pageable)
    }
}