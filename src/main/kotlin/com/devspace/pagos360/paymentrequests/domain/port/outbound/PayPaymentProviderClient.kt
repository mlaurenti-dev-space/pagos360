package com.devspace.pagos360.paymentrequests.domain.port.outbound

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import reactor.core.publisher.Mono

/**
 * Interface defining the client for interacting with the payment provider.
 * Provides methods to create a charge for a payment request.
 */
interface PayPaymentProviderClient {
    fun createCharge(request: PayRequest): Mono<PayCheckoutResponse>
}