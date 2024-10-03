package com.van1164.lottoissofar.review.controller

import com.van1164.lottoissofar.common.auth.service.AuthService
import com.van1164.lottoissofar.common.domain.Review
import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.fixture.builder.ReviewFixtureBuilder
import com.van1164.lottoissofar.fixture.UserFixture
import com.van1164.lottoissofar.review.repository.ReviewRepository
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.user.service.UserService
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReviewControllerTest @Autowired constructor(
    val userRepository: UserJpaRepository,
    val reviewRepository: ReviewRepository,
    val mockMvc: MockMvc
){
    lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = UserFixture.createUser()
        userRepository.save(user)
    }

    //FIXME : `org.junit.jupiter.api.extension.ParameterResolutionException: Failed to resolve parameter [com.van1164.lottoissofar.user.repository.UserJpaRepository userRepository] in constructor [public com.van1164.lottoissofar.review.controller.ReviewControllerTest(com.van1164.lottoissofar.user.repository.UserJpaRepository,com.van1164.lottoissofar.review.repository.ReviewRepository,org.springframework.test.web.servlet.MockMvc)]: Failed to load ApplicationContext for [WebMergedContextConfiguration@9443150 testClass = com.van1164.lottoissofar.review.controller.ReviewControllerTest, locations = [], classes = [com.van1164.lottoissofar.LottoIsSoFarApplication], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = ["org.springframework.boot.test.context.SpringBootTestContextBootstrapper=true"], contextCustomizers = [org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@4b3fa0b3, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@f316aeb, [ImportsContextCustomizer@60bdba16 key = [org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebDriverAutoConfiguration, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration, org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration, org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration, org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration, org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityConfiguration, org.springframework.boot.test.autoconfigure.web.servlet.MockMvcWebClientAutoConfiguration, org.springframework.boot.test.autoconfigure.web.reactive.WebTestClientAutoConfiguration]], org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@76012793, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7a1a3478, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@0, org.springframework.boot.test.web.client.TestRestTemplateContextCustomizer@5023bb8b, org.springframework.boot.test.context.SpringBootTestAnnotation@5733a0b1], resourceBasePath = "src/main/webapp", contextLoader = org.springframework.boot.test.context.SpringBootContextLoader, parent = null]`
    @Test
    @WithMockUser
    fun getAll() {
/*
        mockMvc.get("/api/v1/reviews") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
        }
*/
    }

    @Test
    fun getAllWithUser() {
    }

    @Test
    fun createNewReview() {
    }

}