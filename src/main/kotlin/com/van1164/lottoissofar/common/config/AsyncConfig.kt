package com.van1164.lottoissofar.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor

@Configuration
@EnableAsync
class AsyncConfig {

    @Bean(name = ["taskExecutor"])
    fun taskExecutor(): Executor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 4 // 기본 스레드 수
        executor.maxPoolSize = 10 // 최대 스레드 수
        executor.queueCapacity = 100 // 큐 용량
        executor.setThreadNamePrefix("Async-") // 스레드 이름 접두사
        executor.initialize()
        return executor
    }
}