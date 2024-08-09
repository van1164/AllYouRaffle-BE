package com.van1164.lottoissofar.common.test

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AsyncComponent {

    @Async("taskExecutor")
    fun asyncFun1(){
        println("asyncFun1: " + Thread.currentThread())
        Thread.sleep(1000L)
    }

    @Async("taskExecutor")
    fun asyncFun2(){
        println("asyncFun2: " + Thread.currentThread())
        Thread.sleep(10000L)
    }
}