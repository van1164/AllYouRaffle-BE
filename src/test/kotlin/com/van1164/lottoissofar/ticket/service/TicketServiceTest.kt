package com.van1164.lottoissofar.ticket.service

import com.van1164.lottoissofar.common.domain.TicketHistory
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.fixture.UserFixture
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.user.service.UserService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
class TicketServiceTest @Autowired constructor(
    private val ticketService: TicketService,
    private val userJpaRepository: UserJpaRepository,
    private val ticketHistoryRepository: TicketHistoryRepository
) {

//    lateinit var user: User
    @BeforeEach
    fun before() {
//        userJpaRepository.deleteAll()
//        ticketHistoryRepository.deleteAll()
//        user = UserFixture.createUser()
//        userJpaRepository.save(user)
//
//        for(i: Int in listOf(70,80,90,10,20,30,40,50)){
//            val ticket = TicketHistory(user.userId,i)
//            ticket.createdDate = LocalDateTime.now().minusDays(i.toLong())
//            println(ticket.createdDate)
//            ticketService.saveTicket(ticket)
//        }
//        val tickets = ticketHistoryRepository.findAllByUserIdOrderByCreatedDateDesc(user.userId)
//        tickets.zip(listOf(70,80,90,10,20,30,40,50)).forEach {
//            it.first.createdDate = it.first.createdDate?.minusMinutes(it.second.toLong())
//            ticketHistoryRepository.save(it.first)
//        }
    }

    @Test
    fun getTicketsLast1h() {
//        val tickets = ticketService.getTicketsLast1h("b272982a-3c98-41db-8342-b55b0c6e7672")
//        assertEquals(5, tickets.size)
//        assertArrayEquals(tickets.map{it.ticketCount}.sortedDescending().toTypedArray(), arrayOf(50,40,30,20,10))
    }
}