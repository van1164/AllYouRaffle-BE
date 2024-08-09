package com.van1164.lottoissofar.common.discord

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DiscordService(val restTemplate: RestTemplate) {

    private val discordWebhookUrl = "https://discord.com/api/webhooks/1270590201286234184/c9wLn_xb24jWaLnt8cdRJPPDC7hwe0h3oiadVc8M4eB7In6BrZYHMbz0HzwV9_9z7MBc" // 앞에서 복사한 웹훅 URL을 여기에 붙여넣습니다.

    fun sendMessage(message: String) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val body = mapOf("content" to message)

        val entity = HttpEntity(body, headers)

        val response = restTemplate.exchange(
            discordWebhookUrl,
            HttpMethod.POST,
            entity,
            String::class.java
        )

        if (response.statusCode.is2xxSuccessful) {
            println("Message sent successfully!")
        } else {
            println("Failed to send message: ${response.statusCode}")
        }
    }
}