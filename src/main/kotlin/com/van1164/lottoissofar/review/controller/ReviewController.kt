package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.review.CreateReviewDto
import com.van1164.lottoissofar.common.dto.review.ReadReviewDto
import com.van1164.lottoissofar.common.response.CursorPage
import com.van1164.lottoissofar.review.service.ReviewService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class ReviewController (
    private val reviewService: ReviewService
) {
    @GetMapping("/reviews")
    fun getAll(
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(required = false, defaultValue = "5") size: Int
    ): CursorPage<ReadReviewDto> {
        val effectiveCursor = cursor ?: Long.MAX_VALUE
        val result = reviewService.getAll(effectiveCursor, size)
        val nextCursor = result.content.lastOrNull()?.id

        return CursorPage(result.content, nextCursor, result.hasNext())
    }

    @GetMapping("/user/reviews")
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

    @PostMapping(value = ["/reviews"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createReview(
        @Parameter(hidden = true) user: User,
        @RequestPart(required = false) image: MultipartFile?,
        @RequestPart createReviewDto: CreateReviewDto
    ): ResponseEntity<String> {
        val review = reviewService.createReview(user, createReviewDto, image)
        return ResponseEntity.ok().body(review.toString())
    }
}