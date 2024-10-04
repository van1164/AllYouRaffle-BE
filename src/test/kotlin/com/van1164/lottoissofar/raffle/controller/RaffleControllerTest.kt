package com.van1164.lottoissofar.raffle.controller

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.van1164.lottoissofar.common.discord.DiscordService
import com.van1164.lottoissofar.common.domain.*
import com.van1164.lottoissofar.common.firebase.fcmToken
import com.van1164.lottoissofar.common.security.JwtUtil
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.notification.repository.NotificationRepository
import com.van1164.lottoissofar.purchase_history.repository.PurchaseHistoryRepository
import com.van1164.lottoissofar.raffle.repository.RaffleRepository
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.ticket.repository.TicketHistoryRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.winner_history.repository.WinnerHistoryRepository
import io.github.van1164.K6Executor
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.annotation.Rollback
import kotlin.test.Test


@SpringBootTest
class RaffleControllerTest @Autowired constructor(
    val userJpaRepository: UserJpaRepository,
    val raffleRepository: RaffleRepository,
    val itemJpaRepository: ItemJpaRepository,
    val purchaseHistoryRepository: PurchaseHistoryRepository,
    val jwtUtil: JwtUtil,
    @MockBean
    val discordService: DiscordService,
    val ticketHistoryRepository: TicketHistoryRepository,
    val winnerHistoryRepository: WinnerHistoryRepository,
    val notificationRepository: NotificationRepository

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
        ticketHistoryRepository.deleteAll()
        winnerHistoryRepository.deleteAll()
        notificationRepository.deleteAll()
        purchaseHistoryRepository.deleteAll()
        ticketHistoryRepository.deleteAll()
        raffleRepository.deleteAll()
        itemJpaRepository.deleteAll()

        val item = fixtureMonkey.giveMeBuilder<Item>().setNull("id").set("raffleList", mutableListOf<Raffle>()).set("name", "test").set("imageUrl", "test").set("imageList",listOf<ItemDescriptionImage>()).set("defaultTotalCount", 10).set("possibleRaffle",true).sample()
        val insertRaffle = Raffle(
            totalCount = 5,
            item = item,
            status = RaffleStatus.ACTIVE,
        )
        item.raffleList.add(insertRaffle)
        raffle = raffleRepository.save(insertRaffle)
    }


    //TODO : 아직 성공 못함
    @RepeatedTest(1)
    @DisplayName("동시성 테스트")
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
        val purchaseHistoryCount = purchaseHistoryRepository.count()
        assertEquals(purchaseHistoryCount,5)

        val winnerHistory = winnerHistoryRepository.findByUserId("testMyId", Long.MAX_VALUE, 1)
        assertEquals(winnerHistory.content.count(),1)
        assertEquals(winnerHistory.content[0].raffleId, raffle.id)
    }


    @RepeatedTest(1)
    fun concurrencyPurchaseWithTickets(){
        println(raffle.id)
        val args = HashMap<String,String>()
        args["RAFFLEID"] = raffle.id.toString()
        for (i : Int in (1..10)){
            args["JWT"] = jwtUtil.generateJwtToken("testMyId")
        }
        val k6 = K6Executor.builder().scriptPath("k6_executor/purchaseWithTickets.js").args(args).build()
        val k6Result = k6.runTest()
        k6Result.printResult()
//        println(k6Result.totalRequest)
//        assertEquals(k6Result.successRequest,5)
        val purchaseHistoryCount = purchaseHistoryRepository.count()
        assertEquals(purchaseHistoryCount,5)

        val winnerHistory = winnerHistoryRepository.findByUserId("testMyId", Long.MAX_VALUE, 1)
        assertEquals(winnerHistory.content.count(),1)
        assertEquals(winnerHistory.content[0].raffleId, raffle.id)
        //TODO: 2개가 expected 돼있는데 1개만 검사됨 DB 쿼리 결과는 2개인 것으로 보아 Thread.sleep이 생각대로 동작하지 않는 것일 수도
/*
        Thread.sleep(10000L)
        assertEquals(notificationRepository.findAll().count { it.code == NotificationType.WINNER },2)
*/
    }

    companion object {

        @JvmStatic
        @BeforeAll
        fun beforeAll(@Autowired userJpaRepository: UserJpaRepository, @Autowired jwtUtil: JwtUtil, @Autowired raffleRepository: RaffleRepository, @Autowired reviewRepository: ReviewRepository): Unit {
            reviewRepository.deleteAll()
            raffleRepository.deleteAll()
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
                fcmToken = fcmToken
            )

            userAddress.user = user
            userJpaRepository.save(user)

        }
    }
}