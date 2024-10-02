package com.van1164.lottoissofar.common.firebase

import com.google.firebase.FirebaseApp
import com.van1164.lottoissofar.fixture.UserFixture
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class FirebaseServiceTest @Autowired constructor(
    val firebaseService: FirebaseService
) {


    @Test
    fun sendNotification() {
        val token = "dGkqJwrLRzOCm0FMXVAUe_:APA91bEgCu5spCROmfmufPFTMq9DQBFjghtvAgIFOabsC4-Z6Yy_A6LyMUbT-VnIQmyNWnPT07q4IVze8A4UTpsViDeWEg9iXCUgqy9vKRorPv6KY5yFkcTQNBiKmTnNcYeCZgLixhTD"
        firebaseService.sendNotification(token,"테스트","테스트")
    }
}
const val fcmToken = "dGkqJwrLRzOCm0FMXVAUe_:APA91bEgCu5spCROmfmufPFTMq9DQBFjghtvAgIFOabsC4-Z6Yy_A6LyMUbT-VnIQmyNWnPT07q4IVze8A4UTpsViDeWEg9iXCUgqy9vKRorPv6KY5yFkcTQNBiKmTnNcYeCZgLixhTD"