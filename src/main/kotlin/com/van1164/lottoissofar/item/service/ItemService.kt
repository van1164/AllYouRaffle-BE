package com.van1164.lottoissofar.item.service

import com.van1164.lottoissofar.common.dto.item.CreateItemDto
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.common.s3.S3Component
import com.van1164.lottoissofar.common.util.softDelete
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime


@Service
class ItemService(
    private val itemJpaRepository: ItemJpaRepository,
    private val s3Component: S3Component
) {

    @Transactional
    fun create(createItemDto: CreateItemDto, image: MultipartFile?) : ResponseEntity<Any>{
        val imageUrl = image?.let { s3Component.imageUpload(it) }
        itemJpaRepository.save(createItemDto.toDomain(imageUrl))
        return ResponseEntity.ok().build()
    }

    @Transactional
    fun delete(id : Long){
        softDelete(id,itemJpaRepository)
        itemJpaRepository.findById(id).run {
            val item = this.orElseThrow{GlobalExceptions.NotFoundException()}
            item.deletedDate= LocalDateTime.now()
        }
    }
}