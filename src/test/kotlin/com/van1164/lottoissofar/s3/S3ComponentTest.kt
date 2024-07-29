package com.van1164.lottoissofar.s3

import com.van1164.lottoissofar.common.s3.S3Component
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import kotlin.test.Test

@SpringBootTest
class S3ComponentTest @Autowired constructor(
    private val s3Component: S3Component
) {

    @Test
    fun uploadTest(){
        assertDoesNotThrow {
            val mockMultipart = MockMultipartFile("test",null)
            s3Component.imageUpload(mockMultipart)
        }
    }
}