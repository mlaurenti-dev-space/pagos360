package com.devspace.pagos360.paymentrequests.infrastructure.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PayWebClientConfig {

    @Bean
    fun pagos360WebClient(
        @Value("\${pagos360.api.base-url}") baseUrl: String,
        @Value("\${pagos360.api.api-key}") apiKey: String
    ): WebClient = WebClient.builder()
        .baseUrl(baseUrl)
        .defaultHeader("Authorization", "Bearer $apiKey")
        .build()

}