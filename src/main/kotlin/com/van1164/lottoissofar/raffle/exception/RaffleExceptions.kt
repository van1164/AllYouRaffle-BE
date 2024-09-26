package com.van1164.lottoissofar.raffle.exception

import com.van1164.lottoissofar.common.exception.ErrorCode
import com.van1164.lottoissofar.common.exception.ErrorCode.*

class RaffleExceptions {
    open class RaffleException(val errorCode: ErrorCode) : RuntimeException()

    class AlreadyFinishedException(errorCode: ErrorCode) : RaffleException(errorCode)

    class ExceedTickets(errorCode: ErrorCode = RAFFLE_INSUFFICIENT_TICKETS) : RaffleException(errorCode)

    class TotalTicketExceed(errorCode: ErrorCode = RAFFLE_PURCHASE_LIMIT_EXCEEDED) : RaffleException(errorCode)

    class AlreadyPurchasedException(errorCode: ErrorCode) : RaffleException(errorCode)
}