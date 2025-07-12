package com.devspace.pagos360.paymentrequests.infrastructure

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayResponse
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentRequestProviderClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PayPagos360PaymentRequestClientAdapter(private val webClient: WebClient) : PayPaymentRequestProviderClient {
    override fun createPaymentRequest(request: PayRequest): Mono<PayResponse> =
        webClient.post()
            .uri("/payment-request")
            .bodyValue(mapOf(
                "payment_request" to mapOf(    // según el ejemplo, el payload va anidado
                    "description"     to request.description,
                    "first_due_date"  to request.firstDueDate.date.toString(),
                    "first_total"     to request.firstTotal.amount,
                    "payer_name"      to request.payerName
                )
            ))
            .retrieve()
            .bodyToMono(PayPagos360ChargeResponse::class.java)
            .map { resp ->
                PayResponse(
                    requestId = request.id,
                    checkoutUrl = resp.checkoutUrl
                )
            }
}
