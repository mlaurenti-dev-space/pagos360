package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.application.dto.PayPatchRequestStatusCmd
import com.devspace.pagos360.paymentrequests.application.dto.PayResponseDto
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

/** * Controller to handle webhook events from the payment provider.
 * It marks the payment request as paid when a webhook event is received.
 *
 * Endpoint de webhook para recibir notificaciones de pago
 */
@RestController
@RequestMapping("/api/webhook/payment-requests")
@Tag(name = "Payment Requests Webhook")
class PayRequestWebhookController(
    private val useCases: PayRequestUseCases
) {

    @PatchMapping("/{id}/status")
    @Operation(summary = "Receive webhook event from payment provider")
    fun webhook(
        @Parameter(description = "Token for authentication", required = true)
        @PathVariable id: UUID,
        @RequestBody cmd: PayPatchRequestStatusCmd
    ): Mono<PayResponseDto> =
        useCases.updateStatus(id, cmd.toStatus()).map { it.toDto() }
}