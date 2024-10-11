package com.van1164.lottoissofar.question_post.controller

import com.van1164.lottoissofar.common.domain.QuestionPost
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.question_post.request.CreateQuestionPostRequest
import com.van1164.lottoissofar.common.dto.question_post.response.ReadQuestionPostResponse
import com.van1164.lottoissofar.question_post.service.QuestionPostService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.data.domain.Page
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/question")
class QuestionPostController(
    private val questionPostService: QuestionPostService
) {
    @PostMapping(
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun create(
        @Parameter(hidden = true) user: User,
        @RequestPart request: CreateQuestionPostRequest
    ): ResponseEntity<QuestionPost> {
        val result = questionPostService.create(user, request)
        return ResponseEntity.ok(result)
    }


    @GetMapping
    fun getAll(
        @Parameter(hidden = true) user: User,
        @RequestParam page: Int,
        @RequestParam size: Int
    ): ResponseEntity<Page<ReadQuestionPostResponse>> {
        val result = questionPostService.getAll(page, size)
        return ResponseEntity.ok(result)
    }
}