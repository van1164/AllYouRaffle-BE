package com.van1164.lottoissofar

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class LottoIsSoFarApplication

fun main(args: Array<String>) {
    runApplication<LottoIsSoFarApplication>(*args)
}
