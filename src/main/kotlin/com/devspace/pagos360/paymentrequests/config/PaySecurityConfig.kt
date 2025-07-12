package com.devspace.pagos360.paymentrequests.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.web.server.SecurityWebFilterChain
import javax.crypto.spec.SecretKeySpec


@Configuration
class PaySecurityConfig(
    @Value("\${SECRET_KEY}") private val secretKey: String
) {
    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder {
        // HS256 secret → SecretKeySpec
        val keyBytes = secretKey.toByteArray()
        val keySpec = SecretKeySpec(keyBytes, MacAlgorithm.HS256.name)
        return NimbusReactiveJwtDecoder.withSecretKey(keySpec)
            .macAlgorithm(MacAlgorithm.HS256)
            .build()
    }

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .csrf { it.disable() }
            .authorizeExchange {
                it
                    .pathMatchers(HttpMethod.POST, "/api/payment-requests/webhook").authenticated()
                    .anyExchange().permitAll()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { }
            }
        return http.build()
    }
}