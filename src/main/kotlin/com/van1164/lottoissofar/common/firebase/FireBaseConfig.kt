package com.van1164.lottoissofar.common.firebase

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream
import java.io.IOException


@Configuration
class FirebaseConfig {
    @Bean
    @Throws(IOException::class)
    fun firebaseApp(): FirebaseApp {
        val serviceAccount = ClassPathResource("allyouraffleFirebaseKey.json").inputStream
//            FileInputStream("src/main/resources/firebase-adminsdk.json")

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        return FirebaseApp.initializeApp(options)
    }
}