package com.van1164.lottoissofar.post.repository

import com.van1164.lottoissofar.common.domain.Post
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface PostRepository : JpaRepository<Post,Long> {

    fun findAllByDeadLineAfter(currentDate : LocalDate) : List<Post>
}