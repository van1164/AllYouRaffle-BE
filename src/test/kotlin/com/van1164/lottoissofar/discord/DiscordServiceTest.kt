package com.van1164.lottoissofar.discord

import com.van1164.lottoissofar.common.discord.DiscordService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DiscordServiceTest @Autowired constructor(
    private val discordService : DiscordService
){
    @Test
    fun discordTest(){
        discordService.sendMessage("test")
    }
}