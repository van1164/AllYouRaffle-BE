package com.van1164.lottoissofar.raffle.exception

import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RaffleExceptionHandler {

    @ExceptionHandler(RaffleExceptions.RaffleException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun raffleExceptionHandle(e : RaffleExceptions.RaffleException): ErrorResponse {
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }

}