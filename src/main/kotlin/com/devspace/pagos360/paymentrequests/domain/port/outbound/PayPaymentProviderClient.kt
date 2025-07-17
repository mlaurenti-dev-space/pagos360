package com.devspace.pagos360.paymentrequests.domain.port.outbound

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayResponse
import reactor.core.publisher.Mono

/**
 * Interface defining the client for interacting with the payment provider.
 * Provides methods to create a charge for a payment request.
 */
interface PayPaymentProviderClient {
    fun createPayment(request: PayRequest): Mono<PayResponse>
}