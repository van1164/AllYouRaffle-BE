package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.CreateReviewDto
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.review.service.ReviewService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/")
class ReviewController(
    private val reviewService: ReviewService
) {
    @GetMapping("reviews")
    fun getAll(
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): CursorPage<ReadReviewDto> {
        val effectiveCursor = cursor ?: Long.MAX_VALUE
        val result = reviewService.getAll(effectiveCursor, size)
        val nextCursor = result.content.lastOrNull()?.id

        return CursorPage(result.content, nextCursor, result.hasNext())
    }

    @GetMapping("user/reviews")
    fun getAllWithUser(
        @Parameter(hidden = true) user: User,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): CursorPage<ReadReviewDto> {
        val effectiveCursor = cursor ?: Long.MAX_VALUE
        val result = reviewService.getAllWithUser(user, effectiveCursor, size)
        val nextCursor = result.content.lastOrNull()?.id

        return CursorPage(result.content, nextCursor, result.hasNext())
    }

    @PostMapping("user/reviews")
    fun createNewReview(
        @Parameter(hidden = true) user: User,
        createReviewDto: CreateReviewDto
    ): ResponseEntity<Void> {
        reviewService.createNewReview(user, createReviewDto)
        return ResponseEntity.ok().build()
    }
}