package com.van1164.lottoissofar.test

import com.van1164.lottoissofar.common.test.TestService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AsyncTest @Autowired constructor(
    private val testService: TestService
) {

    @Test
    fun test(){
        testService.asyncTest()
    }

}