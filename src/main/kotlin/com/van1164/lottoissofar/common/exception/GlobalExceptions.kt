package com.van1164.lottoissofar.common.exception

class GlobalExceptions {
    open class GlobalException : RuntimeException()
    class NotFoundException(override val message : String = "찾을 수 없습니다.") :GlobalException()

    class InternalErrorException(override val message: String = "내부 오류 발생") : GlobalException()
}