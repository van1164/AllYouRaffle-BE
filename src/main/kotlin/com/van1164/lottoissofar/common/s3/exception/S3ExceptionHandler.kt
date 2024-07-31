package com.van1164.lottoissofar.common.s3.exception

import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class S3ExceptionHandler {

    @ExceptionHandler(S3Exception::class)
    fun exception(
        e: S3Exception
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(
            ErrorResponse(
                message = "이미지 업로드에 실패하였습니다.",
                description = e.message
            )
        )
    }

}