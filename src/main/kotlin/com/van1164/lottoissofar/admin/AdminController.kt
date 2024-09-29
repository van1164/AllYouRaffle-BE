package com.van1164.lottoissofar.admin

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/admin")
class AdminController {

    @GetMapping("/item")
    fun getItem(): String {
        return "item-admin"
    }

    @GetMapping("/item/{itemId}")
    fun getItem(@PathVariable itemId : Long): String {
        return "item-detail-admin"
    }
}