package com.van1164.lottoissofar.fixture.builder

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.User.Companion.createRandomNickname
import com.van1164.lottoissofar.common.domain.UserAddress
import java.util.*

class UserFixtureBuilder {
    private var userId: String = "testUser" + UUID.randomUUID().toString()
    private var email: String = "testEmail" + UUID.randomUUID().toString()
    private var name: String = UUID.randomUUID().toString()
    private var nickname: String = createRandomNickname()
    private var password: String? = UUID.randomUUID().toString()
    private var phoneNumber: String? = null
    private var profileImageUrl: String? = null
    private var tickets: Int = 0
    private var address: UserAddress? = null

    fun userId(userId: String) = apply { this.userId = userId }
    fun email(email: String) = apply { this.email = email }
    fun name(name: String) = apply { this.name = name }
    fun nickname(nickname: String) = apply { this.nickname = nickname }
    fun password(password: String?) = apply { this.password = password }
    fun phoneNumber(phoneNumber: String?) = apply { this.phoneNumber = phoneNumber }
    fun profileImageUrl(profileImageUrl: String?) = apply { this.profileImageUrl = profileImageUrl }
    fun tickets(tickets: Int) = apply { this.tickets = tickets }
    fun address(address: UserAddress?) = apply { this.address = address }

    fun build(): User {
        return User(
            userId = this.userId,
            email = this.email,
            name = this.name,
            nickname = this.nickname,
            password = this.password,
            phoneNumber = this.phoneNumber,
            profileImageUrl = this.profileImageUrl,
            tickets = this.tickets,
            address = this.address
        )
    }
}
