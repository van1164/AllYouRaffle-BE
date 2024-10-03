package com.van1164.lottoissofar.user.service

import com.van1164.lottoissofar.common.domain.Role
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.domain.UserAddress
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import com.van1164.lottoissofar.ticket.service.TicketService
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals


@SpringBootTest
class TicketService @Autowired constructor(
    val userService: UserService,
    val userJpaRepository: UserJpaRepository,
    val ticketService: TicketService,
    val ticketHistoryRepository: TicketHistoryRepository,
    private val reviewRepository: ReviewRepository,
) {
    lateinit var user : User
    @BeforeEach
    fun setup(){
        reviewRepository.deleteAll()
        userJpaRepository.deleteAll()
        val userAddress = UserAddress(
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
        )
        user = User(
            "testMyId",
            "test",
            "test",
            "test",
            "test",
            "010-2932-1164",
            "",
            0,
            userAddress,
            Role.USER,
        )

        userAddress.user = user
        userJpaRepository.save(user)
    }

    @Test
    fun ticketPlusTest(){
        assertEquals(user.tickets,0)
        userService.ticketPlus(user,1)

        val tickets = userJpaRepository.findById(user.id).get().tickets
        assertEquals(tickets,1)
        assertEquals(ticketHistoryRepository.findAllByUserIdOrderByCreatedDateDesc(user.id).count(),1)
    }

}