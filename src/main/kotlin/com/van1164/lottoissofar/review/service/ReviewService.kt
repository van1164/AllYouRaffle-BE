package com.van1164.lottoissofar.review.service

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.CreateReviewDto
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import com.van1164.lottoissofar.common.s3.S3Component
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.winner_history.service.WinnerHistoryService
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class ReviewService (
    private val reviewRepository: ReviewRepository,
    private val winnerHistoryService: WinnerHistoryService,
    private val s3Component: S3Component
){
    @Transactional
    fun createReview(user: User, createReviewDto: CreateReviewDto, image: MultipartFile?): Review{
        val winnerHistory = winnerHistoryService.findById(createReviewDto.winnerHistoryId)
        val imageUrl = image?.let {s3Component.imageUpload(it)}

        val review = reviewRepository.save(createReviewDto.toDomain(user, winnerHistory, imageUrl))
        winnerHistory.review = review
        winnerHistoryService.saveWinnerHistory(winnerHistory)

        return review
    }

    fun getAll(cursor: Long, size: Int): Page<ReadReviewDto>{
        return reviewRepository.findAllPaged(cursor, size)
    }

    fun getAllWithUser(user: User, cursor: Long, size: Int): Page<ReadReviewDto> {
        return reviewRepository.findAllPagedWithUser(user, cursor, size)
    }
}