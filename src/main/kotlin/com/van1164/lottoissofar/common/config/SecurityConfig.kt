package com.van1164.lottoissofar.common.config

import com.van1164.lottoissofar.common.security.CustomOAuth2UserService
import com.van1164.lottoissofar.common.security.JwtRequestFilter
import com.van1164.lottoissofar.common.security.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
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
                it.requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers("/login/**","/auth/**", "/oauth2/**")
                    .permitAll()
                    .requestMatchers("/", "/error", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js")
                    .permitAll()
                    .requestMatchers("/**").authenticated()
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