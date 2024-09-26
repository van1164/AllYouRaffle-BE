package com.van1164.lottoissofar.common.exception

import com.van1164.lottoissofar.common.exception.ErrorCode.*

class GlobalExceptions {
    open class GlobalException(val errorCode: ErrorCode) : RuntimeException()
    class NotFoundException(errorCode: ErrorCode = NOT_FOUND) :GlobalException(errorCode)

    class InternalErrorException(errorCode: ErrorCode = INTERNAL_SERVER_ERROR) : GlobalException(errorCode)
}