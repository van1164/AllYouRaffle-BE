package com.van1164.lottoissofar.test

import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.common.test.TestService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AsyncTest @Autowired constructor(
    private val testService: TestService,
    private val jwtUtil: JwtUtil
) {

    @Test
    fun test(){
        println(jwtUtil.generateJwtToken("google:van1154van@gmail.com"))
    }

}