package com.van1164.lottoissofar.common.config

import com.van1164.lottoissofar.common.security.CustomOAuth2UserService
import com.van1164.lottoissofar.common.security.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuthSuccessHandler: OAuthSuccessHandler
) {
    @Bean
    open fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf(Customizer.withDefaults())
            .logout {
                it.logoutSuccessUrl("/")
            }
            .oauth2Login {
                it.successHandler(oAuthSuccessHandler)
            }
            .oauth2Login (
                Customizer.withDefaults()
            )
        return http.build()
    }
}