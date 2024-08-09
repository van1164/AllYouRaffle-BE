package com.van1164.lottoissofar.common.email

import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.User
import jakarta.mail.internet.MimeMessage
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailService(private val mailSender: JavaMailSender) {

    fun sendEmail(subject: String, raffle: Raffle, winner: User) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
        helper.setTo(winner.email)
        helper.setSubject(subject)
        // 이메일 HTML 템플릿 로드
        val emailTemplate =
            ClassPathResource("templates/raffle-winner.html").inputStream.bufferedReader().use { it.readText() }

        // {{name}} 변수 대체
        val personalizedContent =
            emailTemplate.replace("{{name}}", winner.name)
                .replace("{{imageUrl}}", raffle.item.imageUrl ?: "")
                .replace("{{raffleId}}",raffle.id.toString())
                .replace("{{itemName}}",raffle.item.name)

        helper.setText(personalizedContent, true)

        mailSender.send(message)
    }
}