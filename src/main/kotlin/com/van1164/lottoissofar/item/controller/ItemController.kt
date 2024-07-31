package com.van1164.lottoissofar.item.controller

import com.van1164.lottoissofar.common.dto.item.CreateItemDto
import com.van1164.lottoissofar.item.service.ItemService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/item")
class ItemController(
    private val itemService: ItemService
) {

    @PostMapping("/create")
    fun create(
        @RequestPart(required = false) image : MultipartFile?,
        @RequestPart(required = true) @Valid createItemDto: CreateItemDto
    ): ResponseEntity<Any> {
        return itemService.create(createItemDto,image)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") id : Long){

    }
}