package com.van1164.lottoissofar.item.service

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class ItemServiceTest @Autowired constructor(
    val itemService: ItemService
) {


    @Test
    @DisplayName("item 생성 테스트")
    fun itemCreateTest(){
//        itemService.createItem()
    }
}