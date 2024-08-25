package com.van1164.lottoissofar.raffle.controller

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryJpaRepository
import com.van1164.lottoissofar.raffle.repository.RaffleJpaRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import io.github.van1164.K6Executor
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RaffleControllerTest @Autowired constructor(
    val userJpaRepository: UserJpaRepository,
    val raffleJpaRepository: RaffleJpaRepository,
    val itemJpaRepository: ItemJpaRepository,
    val purchaseHistoryJpaRepository: PurchaseHistoryJpaRepository,
    val jwtUtil: JwtUtil
) {

    var fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .plugin(JakartaValidationPlugin())
        .build()

    lateinit var raffle : Raffle

    @BeforeEach
    @Transactional
    @Rollback
    fun beforeEach() {
        purchaseHistoryJpaRepository.deleteAll()
        raffleJpaRepository.deleteAll()
        itemJpaRepository.deleteAll()

        val item = fixtureMonkey.giveMeBuilder<Item>().setNull("id").set("raffleList", mutableListOf<Raffle>()).set("defaultTotalCount", 10).set("possibleRaffle",true).sample()
        val insertRaffle = Raffle(
            totalCount = 5,
            item = item,
            status = RaffleStatus.ACTIVE,
        )
        item.raffleList.add(insertRaffle)
        raffle = raffleJpaRepository.save(insertRaffle)
    }


    //TODO : 아직 성공 못함
    @RepeatedTest(1)
    fun concurrencyPurchase(){
        println(raffle.id)
        val args = HashMap<String,String>()
        args["RAFFLEID"] = raffle.id.toString()
        for (i : Int in (1..10)){
            args["JWT"] = jwtUtil.generateJwtToken("testMyId")
        }
        val k6 = K6Executor.builder().scriptPath("k6_executor/purchase.js").args(args).build()
        val k6Result = k6.runTest()
        k6Result.printResult()
//        println(k6Result.totalRequest)
//        assertEquals(k6Result.successRequest,5)
        val purchaseHistoryCount = purchaseHistoryJpaRepository.count()
        assertEquals(purchaseHistoryCount,5)
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll(@Autowired userJpaRepository: UserJpaRepository, @Autowired jwtUtil: JwtUtil, @Autowired raffleJpaRepository: RaffleJpaRepository): Unit {
            raffleJpaRepository.deleteAll()
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
            val user = User(
                "testMyId",
                "test",
                "test",
                "test",
                "test",
                "010-2932-1164",
                "",
                10,
                userAddress,
                Role.USER,
            )

            userAddress.user = user
            userJpaRepository.save(user)

        }
    }
}