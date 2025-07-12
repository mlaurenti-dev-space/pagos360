package com.devspace.pagos360.paymentrequests.infrastructure

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayCheckoutResponse
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentProviderClient
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PayPagos360ClientAdapter(private val webClient: WebClient) : PayPaymentProviderClient {
    override fun createCharge(request: PayRequest): Mono<PayCheckoutResponse> =
        webClient.post()
            .uri("/v1/charges")
            .bodyValue(
                mapOf(
                    "id" to request.id.toString(),
                    "description" to request.description,
                    "amount" to request.firstTotal.amount,
                    "due_date" to request.firstDueDate.toString(),
                    "payer_name" to request.payerName
                )
            )
            .retrieve()
            .bodyToMono(PayPagos360ChargeResponse::class.java)
            .map { resp ->
                PayCheckoutResponse(
                    requestId = request.id,
                    checkoutUrl = resp.checkoutUrl
                )
            }
}
