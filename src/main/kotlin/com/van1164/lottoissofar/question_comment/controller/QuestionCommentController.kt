package com.van1164.lottoissofar.question_comment.controller

import com.van1164.lottoissofar.common.domain.QuestionComment
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.dto.question_comment.request.CreateQuestionCommentRequest
import com.van1164.lottoissofar.common.dto.question_comment.request.UpdateQuestionCommentRequest
import com.van1164.lottoissofar.question_comment.service.QuestionCommentService
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/question_comment")
class QuestionCommentController(
    private val questionCommentService: QuestionCommentService
) {
    @PostMapping
    fun create(
        @Parameter(hidden = true) user: User,
        @RequestBody request: CreateQuestionCommentRequest
    ): ResponseEntity<QuestionComment> {
        val result = questionCommentService.create(user, request)
        return ResponseEntity.ok(result)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateQuestionCommentRequest
    ): ResponseEntity<QuestionComment> {
        val result = questionCommentService.update(id, request)
        return ResponseEntity.ok(result)
    }
}