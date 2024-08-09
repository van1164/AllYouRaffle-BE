package com.van1164.lottoissofar.common.test

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service

@Service
@EnableAsync
class TestService(
    private val asyncComponent: AsyncComponent
) {

    fun asyncTest(): String {
        println("start")
        GlobalScope.launch {
            asyncFun2()
        }
        GlobalScope.launch {
            asyncFun1()
        }
        println("end")
        return "end"
    }


    private fun asyncFun1(){
        println("asyncFun1: " + Thread.currentThread())
        Thread.sleep(1000L)
    }

    private fun asyncFun2(){
        println("asyncFun2: " + Thread.currentThread())
        Thread.sleep(10000L)
    }
}