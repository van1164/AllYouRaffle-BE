package com.van1164.lottoissofar.sms

import com.van1164.lottoissofar.common.dto.sms.SmsMessageDto
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest

@Service
class SmsService(
    @Value("\${coolsms.key.public}") private val publicKey: String,
    @Value("\${coolsms.key.secret}") private val secretKey: String,
    @Value("\${server.phoneNumber}") private val phoneNumber : String
) {
    val messageService: DefaultMessageService =
        initialize(publicKey, secretKey, "https://api.coolsms.co.kr")

    fun sendOne(smsMessage : SmsMessageDto): SingleMessageSentResponse? {
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        println(smsMessage)
        val message = Message(
            from = phoneNumber.split("-").joinToString(""),
            to = smsMessage.to.split("-").joinToString(""),
            text = smsMessage.message
            // country = "국가번호"
        )
        val response = messageService.sendOne(SingleMessageSendingRequest(message))
        return response
    }
}