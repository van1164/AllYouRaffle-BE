package com.van1164.lottoissofar.raffle.service

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryJpaRepository
import com.van1164.lottoissofar.raffle.repository.RaffleJpaRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import kotlin.test.assertEquals

@SpringBootTest
class RaffleServiceTest @Autowired constructor(
    val raffleService: RaffleService,
    val userJpaRepository: UserJpaRepository,
    val raffleJpaRepository: RaffleJpaRepository,
    val itemJpaRepository: ItemJpaRepository,
    val purchaseHistoryJpaRepository: PurchaseHistoryJpaRepository,
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
        userJpaRepository.deleteAll()
        raffleJpaRepository.deleteAll()
        itemJpaRepository.deleteAll()

        val userAddress = UserAddress(
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
            userAddress,
            Role.USER
        )
        userAddress.user = user
        userJpaRepository.save(user)
//        user = fixtureMonkey.giveMeBuilder<User>().set("raffleList").set("purchaseRaffles",mutableListOf<Raffle>()).setNull("id").set("userId","van133").sample().let {
//            println(it)
//
//            it.address = userAddress
//            userAddress.user = it
//            userJpaRepository.save(it)
//        }

        item = fixtureMonkey.giveMeBuilder<Item>().setNull("id").set("raffleList", mutableListOf<Raffle>()).set("defaultTotalCount", 10).sample()


        raffle = Raffle(
            totalCount = 10,
            item = item,
            status = RaffleStatus.ACTIVE,
        )
        item.raffleList.add(raffle)
        raffleJpaRepository.save(raffle)

//        raffle = fixtureMonkey.giveMeBuilder<Raffle>().set("item",item).set("status",RaffleStatus.ACTIVE).setNull("id").setNull("winner").set("totalCount", 10)
//            .set("currentCount", 0).sample().let {
//                it.item = item
//                item.raffleList.add(it)
//                raffleJpaRepository.save(it)
//        }
    }

    @Test
    @Transactional
    fun purchaseRaffleTest() {
        var purchaseHistoryId : Long
        assertDoesNotThrow {
            val result = raffleService.purchaseRaffle(raffleId = raffle.id, user = user).body
            println(result)
            purchaseHistoryId =  result!!.id
            println(purchaseHistoryId)
            assertTrue(purchaseHistoryJpaRepository.findById(purchaseHistoryId).isPresent)
        }


        val savedRaffle = raffleJpaRepository.findById(raffle.id).get()

        assertEquals(savedRaffle.currentCount,1)
        assertEquals(savedRaffle.purchaseHistoryList.size,1)
        assertEquals(savedRaffle.purchaseHistoryList[0].id,user.id)

    }

}