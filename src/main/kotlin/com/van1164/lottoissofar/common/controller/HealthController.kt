package com.van1164.lottoissofar.common.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthController {

    @GetMapping("")
    fun isHealth(): ResponseEntity<Any> {
        return ResponseEntity.ok().build<Any>()
    }
}