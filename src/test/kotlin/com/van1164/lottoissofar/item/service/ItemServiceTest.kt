package com.van1164.lottoissofar.item.service

import com.amazonaws.services.s3.AmazonS3Client
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeBuilder
import com.van1164.lottoissofar.common.domain.Item
import com.van1164.lottoissofar.common.domain.Raffle
import com.van1164.lottoissofar.common.domain.RaffleStatus
import com.van1164.lottoissofar.common.dto.item.CreateItemDto
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.item.repository.ItemJpaRepository
import com.van1164.lottoissofar.raffle.repository.RaffleRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockMultipartFile
import kotlin.test.*


@SpringBootTest
class ItemServiceTest @Autowired constructor(
    val itemService: ItemService,
    val itemJpaRepository: ItemJpaRepository,
    @MockBean val s3Component: AmazonS3Client,
    @PersistenceContext val em : EntityManager,
    val raffleRepository: RaffleRepository
) {
    var fixtureMonkey: FixtureMonkey = FixtureMonkey.builder()
        .plugin(KotlinPlugin())
        .plugin(JakartaValidationPlugin())
        .build()


    lateinit var savedItem : Item

    @BeforeEach
    fun setup(){
        savedItem = fixtureMonkey.giveMeBuilder<Item>().set("raffleList", mutableListOf<Raffle>()).setNull("id").sample().let {
            itemJpaRepository.save(it)
        }
    }


    @RepeatedTest(10)
    @DisplayName("item 생성 테스트")
    fun itemCreateTest(){

        //given
        val createItemDto = fixtureMonkey.giveMeBuilder<CreateItemDto>().sample()
        val mockImage = MockMultipartFile("testImage",null)
        println(createItemDto)

        //when
        val response = itemService.create(createItemDto,mockImage)
        val itemResult = itemJpaRepository.findByName(createItemDto.name)

        assertEquals(response.statusCode,HttpStatus.OK)
        assertNotNull(itemResult)
        println(itemResult)
    }

    @Test
    @DisplayName("item 삭제 테스트")
    fun deleteSuccess(){
        //given
//        val savedItem = fixtureMonkey.giveMeBuilder<Item>().setNull("id").sample().let {
//            itemJpaRepository.save(it)
//        }
        assertNull(savedItem.deletedDate)
        val savedId = savedItem.id
        println(savedId)
        em.clear()

        //when
        itemService.delete(savedId)
        em.clear()
        val item = itemJpaRepository.findById(savedId)

        //then
        assertTrue(item.isPresent)
        assertNotNull(item.get().deletedDate)
    }

    @Test
    @DisplayName("item 삭제 실패 테스트")
    fun deleteFail(){
        assertThrows<GlobalExceptions.NotFoundException> {
            itemService.delete(10)
        }
    }

    @Test
    @DisplayName("item 생성 중지")
    fun stop(){
        itemService.stop(savedItem.id)

        em.clear()

        val findItem = itemJpaRepository.findById(savedItem.id)

        assertTrue(findItem.isPresent)
        assertFalse(findItem.get().possibleRaffle)

    }

    @Test
    @DisplayName("item 재개 및 raffle 시작 확인")
    fun start(){
        itemService.stop(savedItem.id)

        em.clear()

        val findItem = itemJpaRepository.findById(savedItem.id)

        assertTrue(findItem.isPresent)
        assertFalse(findItem.get().possibleRaffle)

        val raffle = itemService.start(findItem.get().id).body!!

        em.clear()

        val startedItem = itemJpaRepository.findById(savedItem.id)

        assertTrue(startedItem.isPresent)
        assertTrue(startedItem.get().possibleRaffle)


        val startedRaffle = raffleRepository.findById(raffle.id)

        assertTrue(startedRaffle.isPresent)
        assertEquals(startedRaffle.get().status,RaffleStatus.ACTIVE)
        assertEquals(startedRaffle.get().currentCount,0)
    }
}