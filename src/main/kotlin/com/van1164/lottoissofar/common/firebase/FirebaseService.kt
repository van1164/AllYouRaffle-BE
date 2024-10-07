package com.van1164.lottoissofar.common.firebase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Service


@Service
class FirebaseService {
    fun sendNotification(token: String?, title: String, body: String) {
        if (token == null) {
            return
        }
        val notification: Notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()

        val message: Message = Message.builder()
            .setToken(token)
            .setNotification(notification)
            .build()

        try {
            val response = FirebaseMessaging.getInstance().send(message)
            println("Successfully sent message: $response")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 여러 사용자에게 알림을 한 번에 전송하는 메서드
     *
     * @param tokens 사용자들의 FCM 토큰 목록
     * @param title  알림 제목
     * @param body   알림 내용
     */
    fun sendMulticastNotification(tokens: List<String?>, title: String, body: String) {
        val notification = Notification.builder()
            .setTitle(title)
            .setBody(body)
            .build()

        val message = MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(tokens)
            .build()

        try {
            val response = FirebaseMessaging.getInstance().sendEachForMulticast(message)
            println("Successfully sent message: " + response.successCount + " messages were sent successfully")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}