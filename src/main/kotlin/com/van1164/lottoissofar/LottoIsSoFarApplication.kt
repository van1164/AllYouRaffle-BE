package com.van1164.lottoissofar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication//(exclude = [SecurityAutoConfiguration::class])
@EnableJpaAuditing
class LottoIsSoFarApplication

fun main(args: Array<String>) {
    runApplication<LottoIsSoFarApplication>(*args)
}
