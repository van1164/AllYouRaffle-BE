package com.van1164.lottoissofar.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig(
    @Value("\${server.url}") private val serverUrl :String
) {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .addServersItem(Server().url(serverUrl))
            .info(Info().title("API Documentation").version("1.0"))
            .addSecurityItem(SecurityRequirement().addList("bearer-jwt"))
            .components(
                io.swagger.v3.oas.models.Components()
                    .addSecuritySchemes(
                        "bearer-jwt",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    )
            )
    }
}