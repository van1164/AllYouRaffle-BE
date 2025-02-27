package com.van1164.lottoissofar.fixture.builder

import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User

class ReviewFixtureBuilder {
    private var title: String = "Default Title"
    private var description: String = "Default Description"
    private var imageUrl: String? = null
    private var user: User? = null

    fun title(title: String) = apply { this.title = title }
    fun description(description: String) = apply { this.description = description }
    fun imageUrl(imageUrl: String?) = apply { this.imageUrl = imageUrl }
    fun user(user: User) = apply { this.user = user }

    fun build(): Review {
        return Review(
            title = this.title,
            description = this.description,
            imageUrl = this.imageUrl,
            user = this.user ?: throw IllegalStateException("User must be provided")
        )
    }
}
