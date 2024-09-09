package com.van1164.lottoissofar.item.controller

import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.dto.item.CreateItemDto
import com.van1164.lottoissofar.common.dto.item.StartItemDto
import com.van1164.lottoissofar.item.service.ItemService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/api/v1/item")
class ItemController(
    private val itemService: ItemService
) {

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    fun create(
        @RequestPart(required = false) image : MultipartFile?,
        @RequestPart(required = true) @Valid createItemDto: CreateItemDto
    ): ResponseEntity<Any> {
        return itemService.create(createItemDto,image)
    }

    @PostMapping("/create_description_image/{itemId}")
    fun createDescriptionImage(
        @RequestPart(required = true) image : MultipartFile,
        @PathVariable(name = "itemId") itemId : Long
    ): ResponseEntity<Any> {
        return itemService.createDescriptionImage(itemId,image)
    }

    @PostMapping("/stop/{id}")
    fun stop(@PathVariable("id") id : Long): ResponseEntity<Any> {
        return itemService.stop(id)
    }

    @PostMapping("/start/{id}")
    fun start(
        @PathVariable("id") id : Long,
        @RequestBody(required = false) startItemDto: StartItemDto
             ): ResponseEntity<Raffle> {

        println(startItemDto.totalCount)
        println(id)
        println(startItemDto.ticketPrice)
        return itemService.start(id,startItemDto.totalCount,startItemDto.ticketPrice)
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") id : Long){
        return itemService.delete(id)
    }
}