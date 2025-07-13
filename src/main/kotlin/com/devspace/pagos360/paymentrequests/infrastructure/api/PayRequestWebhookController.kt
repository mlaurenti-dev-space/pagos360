package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.util.UUID

/** * Controller to handle webhook events from the payment provider.
 * It marks the payment request as paid when a webhook event is received.
 *
 * Endpoint de webhook para recibir notificaciones de pago
 */
@RestController
@RequestMapping("/api/payment-requests/webhook")
class PayRequestWebhookController(
    private val useCases: PayRequestUseCases
) {
    @PostMapping
    fun webhook(
        @Parameter(description = "Token for authentication", required = true)
        @RequestHeader("Bearer") token: String,
        @RequestBody event: PayWebhookEvent
    ): Mono<Void> =
        useCases.markPaid(UUID.fromString(event.id)).then()
}