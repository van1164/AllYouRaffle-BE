package com.van1164.lottoissofar.raffle.service

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryRepository
import com.van1164.lottoissofar.raffle.exception.RaffleExceptions
import com.van1164.lottoissofar.raffle.repository.RaffleRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback

@SpringBootTest
class RaffleWithTicketTest @Autowired constructor(
    val raffleService: RaffleService,
    val userJpaRepository: UserJpaRepository,
    val raffleJpaRepository: RaffleRepository,
    val itemJpaRepository: ItemJpaRepository,
    val purchaseHistoryRepository: PurchaseHistoryRepository,
    @PersistenceContext val em : EntityManager
) {
    var fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .plugin(JakartaValidationPlugin())
        .build()

    lateinit var user: User
    lateinit var item: Item
    lateinit var raffle: Raffle



    @BeforeEach
    @Transactional
    @Rollback
    fun beforeEach() {
        em.clear()
        raffleJpaRepository.deleteAll()
        itemJpaRepository.deleteAll()
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
            "test",
            "test",
            "test",
            "test",
            "test",
            "test",
            "",
            70,
            userAddress,
            Role.USER
        )
        userAddress.user = user
        userJpaRepository.save(user)

        item = fixtureMonkey.giveMeBuilder<Item>().setNull("id").set("raffleList", mutableListOf<Raffle>()).set("imageList",listOf<ItemDescriptionImage>()).set("defaultTotalCount", 10).sample()

        raffle = Raffle(
            totalCount = 100,
            currentCount = 50,
            item = item,
            status = RaffleStatus.ACTIVE,
        )
        item.raffleList.add(raffle)
        raffleJpaRepository.save(raffle)

    }


    @Test
    fun `should throw ExceedTickets when user requests more tickets than they have`() {
        // Given
        user.tickets = 0
        userJpaRepository.save(user)
        // When / Then
        assertThrows<RaffleExceptions.ExceedTickets> {
            raffleService.purchaseRaffleOneTicket(raffle.id, user.id)
        }
    }

    @Test
    fun `should throw NotFoundException when raffle is not found`() {
        // Given
        val ticketCount = 5

        // When / Then
        assertThrows<GlobalExceptions.NotFoundException> {
            raffleService.purchaseRaffleOneTicket(Long.MAX_VALUE, user.id)
        }
        assertThrows<GlobalExceptions.NotFoundException> {
            raffleService.purchaseRaffleOneTicket(raffle.id, Long.MAX_VALUE)
        }
    }

    @Test
    fun `should throw AlreadyFinishedException when raffle is not active`() {
        // Given
        val ticketCount = 5
        raffle.status = RaffleStatus.COMPLETED

        raffleJpaRepository.save(raffle)

        // When / Then
        assertThrows<RaffleExceptions.AlreadyFinishedException> {
            raffleService.purchaseRaffleOneTicket(raffle.id, user.id)
        }
    }

    @Test
    fun `should throw AlreadyFinishedException when raffle is already completed`() {
        // Given
        val ticketCount = 5
        raffle.currentCount = raffle.totalCount
        raffleJpaRepository.save(raffle)
        // When / Then
        assertThrows<RaffleExceptions.AlreadyFinishedException> {
            raffleService.purchaseRaffleOneTicket(raffle.id, user.id)
        }
    }

    @Test
    fun `should increase currentCount by ticketCount when purchase is successful`() {
        // Given
        val ticketCount = 5

        // When
        val response: ResponseEntity<MutableList<PurchaseHistory>> =
            raffleService.purchaseRaffleOneTicket(raffle.id, user.id)

        val resultRaffle = raffleJpaRepository.findById(raffle.id).get()

        // Then
        assertEquals(resultRaffle.currentCount, 51)
        assertTrue(response.body!!.isNotEmpty())
        assertEquals(response.body!!.size,1)
    }

    @Test
    fun `should complete raffle when currentCount reaches totalCount`() {
        // Given
        val ticketCount = 50 // Exactly fills the raffle

        raffle.currentCount = raffle.totalCount-1

        raffleJpaRepository.save(raffle)
        // When
        raffleService.purchaseRaffleOneTicket(raffle.id, user.id)


        val resultRaffle = raffleJpaRepository.findById(raffle.id).get()
        // Then
        assertEquals(resultRaffle.currentCount, raffle.totalCount)
        assertEquals(resultRaffle.status, RaffleStatus.COMPLETED)
    }
}
