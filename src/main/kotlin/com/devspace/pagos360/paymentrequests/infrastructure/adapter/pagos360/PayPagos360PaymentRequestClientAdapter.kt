package com.devspace.pagos360.paymentrequests.infrastructure.adapter.pagos360

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayResponse
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentRequestProviderClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class PayPagos360PaymentRequestClientAdapter(private val webClient: WebClient) : PayPaymentRequestProviderClient {

    private val logger: Logger = LoggerFactory.getLogger(PayPagos360PaymentRequestClientAdapter::class.java)

    override fun createPaymentRequest(request: PayRequest): Mono<PayResponse> {
        logger.info("Creating payment request for id={}, payerName={}", request.id, request.payerName)
        val body = PayPagos360RequestBody(
            paymentRequest = PayPagos360RequestDetail.from(
                description = request.description,
                firstDueDate = request.firstDueDate.date,
                firstTotal = request.firstTotal.amount.toString(),
                payerName = request.payerName
            )
        )

        return webClient.post()
            .uri("/payment-request")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(PayPagos360ChargeResponse::class.java)
            .doOnSubscribe { logger.debug("Subscribed to createPaymentRequest for id={}", request.id) }
            .doOnError { e -> logger.error("Error creating payment request for id={}", request.id, e) } // Podriamos guardar el error en la base de datos json response.
            .map { resp ->
                logger.info("Received response for payment request id={}, checkoutUrl={}", request.id, resp.checkoutUrl)
                PayResponse(
                    requestId = request.id,
                    checkoutUrl = resp.checkoutUrl
                )
            }
    }
}
