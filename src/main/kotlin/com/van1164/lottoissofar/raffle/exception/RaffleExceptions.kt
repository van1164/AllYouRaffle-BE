package com.van1164.lottoissofar.raffle.exception

class RaffleExceptions {
    open class RaffleException(message : String) : RuntimeException()

    class AlreadyFinishedException(message : String) : RaffleException(message)

    class ExceedTickets(message : String = "보유한 티켓이 부족합니다.") : RaffleException(message)

    class TotalTicketExceed(message: String = "이 래플의 구매가능 횟수를 초과했습니다.") : RaffleException(message)

    class AlreadyPurchasedException(message: String) : RaffleException(message)
}