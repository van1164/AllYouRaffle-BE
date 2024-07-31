package com.van1164.lottoissofar.common.exception

import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler{

    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleNotValidException(
        e: BindException,
        bindingResult: BindingResult
    ): ErrorResponse {
        println("validation errors : ${bindingResult.fieldErrors}")

        val defaultMessage = bindingResult.fieldError?.defaultMessage
        val code = bindingResult.fieldError?.code

        val result = ErrorResponse(
            message = defaultMessage,
            description = e.message
        )
        println(result)

        return result
    }

    @ExceptionHandler(GlobalExceptions.GlobalException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDefaultException(
        e: GlobalExceptions.NotFoundException
    ) : ErrorResponse{
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }
}