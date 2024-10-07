package com.van1164.lottoissofar.fixture

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.util.KoreanNameGenerator
import java.util.*

class UserFixture {
    companion object {
        fun createUser(): User {
            return UserFixture().user {}
        }
    }

    internal fun user(block: User.() -> Unit = {}): User {
        val user = User(
            userId = UUID.randomUUID().toString(),
            email = UUID.randomUUID().toString(),
            nickname = "test",
            name = KoreanNameGenerator.generateRandomKoreanName(),
            password = UUID.randomUUID().toString()
        )
        user.apply(block)
        return user
    }

}