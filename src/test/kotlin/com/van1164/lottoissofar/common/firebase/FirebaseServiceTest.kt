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
        val token = fcmToken
        firebaseService.sendNotification(token,"테스트","테스트")
    }
}
const val fcmToken ="your token write"