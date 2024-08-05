package com.van1164.lottoissofar.common.security

import com.van1164.lottoissofar.common.domain.User
import com.van1164.lottoissofar.common.exception.GlobalExceptions
import com.van1164.lottoissofar.user.repository.UserJpaRepository
import com.van1164.lottoissofar.user.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class CustomOAuth2UserService(
    private val userRepository: UserJpaRepository,
    private val userService : UserService,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeName =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName

        println(oauth2User)
        println(registrationId)
        println(userNameAttributeName)
        val email = oauth2User.attributes["email"] as String?
            ?: run { throw GlobalExceptions.InternalErrorException("소셜로그인에서 이메일을 불러올 수 없습니다.") }
        val name = oauth2User.attributes["name"] as String?
            ?: run { throw GlobalExceptions.InternalErrorException("소셜로그인에서 이름을 불러올 수 없습니다.") }
        val userId = "$registrationId:$email"
        println(userId)
//        val attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oauth2User.attributes)

        val user = findOrSave(userId,email,name)
//        httpSession.setAttribute("user", SessionUser(user))

        return DefaultOAuth2User(
            setOf(SimpleGrantedAuthority(user.role.name)),
            oauth2User.attributes,
            userNameAttributeName
        )
    }


    private fun findOrSave(userId: String, email: String, name: String): User {
        val user = userRepository.findUserByUserId(userId)
            ?: User(
                    email = email,
                    name = name,
                    userId = userId
                )
        return userRepository.save(user)
    }
}