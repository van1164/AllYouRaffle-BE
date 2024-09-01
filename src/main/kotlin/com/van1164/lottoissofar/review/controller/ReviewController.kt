package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.CreateReviewDto
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import com.van1164.lottoissofar.review.service.ReviewService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/")
class ReviewController (
    private val reviewService: ReviewService
){
    @GetMapping("review")
    fun getAll(cursor: Long, pageable: Pageable) : Page<ReadReviewDto> {
        return reviewService.getAll(cursor, pageable)
    }

    @GetMapping("user/review")
    fun getAllWithUser(
        @Parameter(hidden = true) user: User, cursor:Long, pageable: Pageable
    ) : Page<ReadReviewDto> {
        return reviewService.getAllWithUser(user, cursor, pageable)
    }

    //FIXME: swagger 반환 값 정상 응답인지 확인 필요
    @PostMapping
    fun createNewReview(
        @Parameter(hidden = true) user: User,
        createReviewDto: CreateReviewDto
    ) : ResponseEntity<Void> {
        reviewService.createNewReview(user, createReviewDto)
        return ResponseEntity.ok().build()
    }
}