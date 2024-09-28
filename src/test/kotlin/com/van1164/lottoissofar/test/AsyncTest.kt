package com.van1164.lottoissofar.test

import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.common.test.TestService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AsyncTest @Autowired constructor(
    private val testService: TestService,
    private val jwtUtil: JwtUtil
) {

    @Test
    @DisplayName("5개의 재고 내 ㅁ구매 동시성 테스트")
    fun test(){
    }

    @Test
    @DisplayName("티켓 구매 동시성 테스트 - 동시 100명")
    fun test2(){
    }


    @Test
    @DisplayName("잔여 티켓 조회 동시성 테스트")
    fun test3(){
    }


}