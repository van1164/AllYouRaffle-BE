package com.van1164.lottoissofar.raffle.exception

import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RaffleExceptionHandler {
    @Order(value = -1)
    @ExceptionHandler(RaffleExceptions.AlreadyFinishedException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun alreadyFinishedExceptionHandle(e : RaffleExceptions.RaffleException): ErrorResponse {
        return ErrorResponse(
            message = e.errorCode.message,
            description = e.message
        )
    }

    @ExceptionHandler(RaffleExceptions.RaffleException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun raffleExceptionHandle(e : RaffleExceptions.RaffleException): ErrorResponse {
        return ErrorResponse(
            message = e.errorCode.message,
            description = e.message
        )
    }

}