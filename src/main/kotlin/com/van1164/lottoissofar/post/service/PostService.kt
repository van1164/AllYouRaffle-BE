package com.van1164.lottoissofar.post.service

import com.van1164.lottoissofar.common.domain.Post
import com.van1164.lottoissofar.common.dto.post.PostCreateDto
import com.van1164.lottoissofar.common.s3.S3Component
import com.van1164.lottoissofar.post.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class PostService(
    private val postRepository: PostRepository,
    private val s3Component : S3Component
) {

    @Transactional
    fun create(
        image:MultipartFile,
        postCreateDto: PostCreateDto
    ){
        val imageUrl = s3Component.imageUpload(image)
        createPost(imageUrl,postCreateDto).run {
            postRepository.save(this)
        }
    }

    @Transactional(readOnly = true)
    fun getAllActive(): List<Post> {
        return postRepository.findAllByDeadLineAfter(LocalDate.now())
    }

    private fun createPost(imageUrl : String, postCreateDto: PostCreateDto): Post {
        return Post(
            imageUrl = imageUrl,
            redirectUrl = postCreateDto.redirectUrl,
            deadLine = postCreateDto.deadLine
        )
    }

}