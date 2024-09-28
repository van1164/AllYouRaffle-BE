package com.van1164.lottoissofar.common.exception

import com.van1164.lottoissofar.common.discord.DiscordService
import com.van1164.lottoissofar.common.dto.response.ErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    val discordService: DiscordService
){
    @ExceptionHandler(ExpiredJwtException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleJWTException(
        e: ExpiredJwtException
    ) : ErrorResponse{
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }
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

    @ExceptionHandler(GlobalExceptions.NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleDefaultException(
        e: GlobalExceptions.NotFoundException
    ) : ErrorResponse{
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }

    @ExceptionHandler(GlobalExceptions.InternalErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleInternalException(
        e: GlobalExceptions.InternalErrorException
    ) : ErrorResponse{
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }



    @OptIn(DelicateCoroutinesApi::class)
    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(
        e: RuntimeException
    ) : ErrorResponse{
        e.printStackTrace()
        GlobalScope.launch {
            var userId : String? =null
            try {
                userId = SecurityContextHolder.getContext().authentication.name
            }catch (e:Exception){}

            discordService.sendMessage("사용자 userId:$userId\n예상치 못한 에러 발생\n메시지:${e.message}\n원인:${e.stackTraceToString().substring(0..1000)}")
        }
        return ErrorResponse(
            message = e.message,
            description = e.message
        )
    }

}