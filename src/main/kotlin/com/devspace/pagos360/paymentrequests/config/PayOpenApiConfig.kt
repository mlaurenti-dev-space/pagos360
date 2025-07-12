package com.devspace.pagos360.paymentrequests.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PayOpenApiConfig {
    @Bean
    fun customOpenAPI(): OpenAPI? {
        return OpenAPI()
            .info(
                Info()
                    .title("Pagos 360 Payment Requests")
                    .version("1.0.0")
                    .description("API for Pagos 360 Payment Requests")
            )
    }
}