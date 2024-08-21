package com.van1164.lottoissofar.sms

import com.van1164.lottoissofar.common.dto.sms.SmsMessageDto
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest
class SmsServiceTest @Autowired constructor(
    private val smsService: SmsService
)
{
    @Test
    fun sendOne(){
        val smsMessageDto = SmsMessageDto(
            "01029381164",
            "01029321164",
            "인증번호 010132"
        )
        smsService.sendOne(smsMessageDto)
    }

}