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
    private val userService: UserService,
    private val httpSession: HttpSession
) : OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest): CustomOAuth2User {
        val delegate = DefaultOAuth2UserService()
        val oauth2User = delegate.loadUser(userRequest)

        val registrationId = userRequest.clientRegistration.registrationId
        val userNameAttributeNameKey =
            userRequest.clientRegistration.providerDetails.userInfoEndpoint.userNameAttributeName
        val userNameAttributeNameValue = oauth2User.attributes[userNameAttributeNameKey] as String?
            ?: run { throw GlobalExceptions.InternalErrorException("소셜로그인에서 이메일을 불러올 수 없습니다.") }
        val email = oauth2User.attributes["email"] as String?
            ?: run { throw GlobalExceptions.InternalErrorException("소셜로그인에서 이메일을 불러올 수 없습니다.") }
        val name = oauth2User.attributes["name"] as String?
            ?: run { throw GlobalExceptions.InternalErrorException("소셜로그인에서 이름을 불러올 수 없습니다.") }

        println("VVVVVVVVVVVVVVVVVVVVVV")
        println(oauth2User.attributes.toString())
        val profileImageUrl = oauth2User.attributes["picture"] as String?
        return customOAuth2User(
            registrationId,
            email,
            name,
            profileImageUrl,
            oauth2User.attributes,
            userNameAttributeNameKey,
            userNameAttributeNameValue
        )
    }

    fun customOAuth2User(
        registrationId: String,
        email: String,
        name: String,
        profileImageUrl: String?,
        attributes: MutableMap<String, Any>? = null,
        userNameAttributeNameKey: String = "sub",
        userNameAttributeNameValue :String
    ): CustomOAuth2User {
        val userId = "$registrationId:$email"

        val user = findOrSave(userId, email, name, profileImageUrl)
        val attr = attributes ?: let {
            mutableMapOf<String, Any>("email" to email, "name" to name,userNameAttributeNameKey to userNameAttributeNameValue).apply {
                profileImageUrl?.run { put("picture", this) }
            }
        }
        println(attr)

        return CustomOAuth2User(
            oauth2User = DefaultOAuth2User(
                setOf(SimpleGrantedAuthority(user.role.key)),
                attr,
                userNameAttributeNameKey
            ),
            userId = userId
        )
    }


    private fun findOrSave(userId: String, email: String, name: String, profileImageUrl: String?): User {
        val user = userRepository.findUserByUserId(userId)
            ?: User(
                email = email,
                name = name,
                userId = userId,
                profileImageUrl = profileImageUrl
            )
        return userRepository.save(user)
    }
}