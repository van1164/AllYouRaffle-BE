package com.van1164.lottoissofar.raffle.exception

class RaffleExceptions {
    open class RaffleException(message : String) : RuntimeException()

    class AlreadyFinishedException(message : String) : RaffleException(message)

    class AlreadyPurchasedException(message: String) : RaffleException(message)
}