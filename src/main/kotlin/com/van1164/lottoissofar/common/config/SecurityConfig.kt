package com.van1164.lottoissofar.common.config

import com.van1164.lottoissofar.common.security.CustomOAuth2UserService
import com.van1164.lottoissofar.common.security.JwtRequestFilter
import com.van1164.lottoissofar.common.security.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter




@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuthSuccessHandler: OAuthSuccessHandler,
    private val jwtRequestFilter: JwtRequestFilter
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf{
                it.disable()
            }
            .authorizeHttpRequests{
                it.requestMatchers("/**").authenticated()
            }
            .logout {
                it.logoutSuccessUrl("/")
            }
            .oauth2Login {
                it.successHandler(oAuthSuccessHandler)
            }
            .oauth2Login (
                Customizer.withDefaults()
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }
}