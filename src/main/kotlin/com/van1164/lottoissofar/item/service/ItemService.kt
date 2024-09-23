package com.van1164.lottoissofar.item.service

import com.van1164.lottoissofar.common.domain.Item
import com.van1164.lottoissofar.common.domain.ItemDescriptionImage
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.dto.item.CreateItemDto
import com.van1164.lottoissofar.common.exception.ErrorCode
import com.van1164.lottoissofar.common.exception.ErrorCode.*
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.s3.S3Component
import com.van1164.lottoissofar.common.util.softDelete
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.raffle.service.RaffleService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile


@Service
class ItemService(
    private val itemJpaRepository: ItemJpaRepository,
    private val s3Component: S3Component,
    private val raffleService: RaffleService
) {

    @Transactional
    fun create(createItemDto: CreateItemDto, image: MultipartFile?): ResponseEntity<Any> {
        val imageUrl = image?.let { s3Component.imageUpload(it) }
        itemJpaRepository.save(createItemDto.toDomain(imageUrl))
        return ResponseEntity.ok().build()
    }

    @Transactional
    fun delete(id: Long) {
        softDelete(id, itemJpaRepository)
    }

    @Transactional
    fun stop(id: Long) : ResponseEntity<Any> {
        val item = findById(id)
        item.possibleRaffle = false
        return ResponseEntity.ok().build()
    }

    @Transactional
    fun start(id: Long, totalCount: Int? = null, ticketPrice: Int? = null): ResponseEntity<Raffle> {
        val item = findById(id)
        item.possibleRaffle = true
        val raffle = raffleService.createNewRaffle(item, totalCount, ticketPrice)
        return ResponseEntity.ok().body(raffle)
    }

    fun findById(id: Long): Item {
        return itemJpaRepository.findById(id).orElseThrow { GlobalExceptions.NotFoundException(
            NOT_FOUND.setMessageWith(id)) }
    }

    @Transactional
    fun createDescriptionImage(itemId: Long, image: MultipartFile): ResponseEntity<Any> {
        val item = findById(itemId)
        val imageUrl =  s3Component.imageUpload(image)
        val itemImage = ItemDescriptionImage(imageUrl,item)
        item.imageList.add(itemImage)
        return ResponseEntity.ok().body(itemImage)
    }


}