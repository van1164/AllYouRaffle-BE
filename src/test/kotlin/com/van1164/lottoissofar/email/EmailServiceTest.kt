package com.van1164.lottoissofar.email

import com.van1164.lottoissofar.common.domain.Item
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus
import com.van1164.lottoissofar.common.domain.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class EmailServiceTest @Autowired constructor(
    private val emailService: EmailService
) {

    @Test
    fun emailSendTest(){
        val raffle = Raffle(
            1,
            1,
            1,
            RaffleStatus.COMPLETED,
            item = Item("",1,"")
        )
        val user = User(
            email = "van1154van@gmail.com",
            name = "test",
            userId = "userId"
        )
        emailService.sendEmail("test",raffle,user)
    }
}