package com.van1164.lottoissofar.post.controller

import com.van1164.lottoissofar.common.domain.Post
import com.van1164.lottoissofar.common.dto.post.PostCreateDto
import com.van1164.lottoissofar.post.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/post")
class PostController(
    private val postService: PostService
) {

    @PostMapping("/create")
    fun create(
        @RequestPart image : MultipartFile,
        @RequestBody postCreateDto: PostCreateDto
    ) : ResponseEntity<Any>{
        postService.create(image,postCreateDto)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/active")
    fun getAllActive(): List<Post> {
        return postService.getAllActive()
    }
}