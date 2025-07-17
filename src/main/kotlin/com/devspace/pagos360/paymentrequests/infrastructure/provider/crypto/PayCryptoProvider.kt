package com.devspace.pagos360.paymentrequests.infrastructure.provider.crypto

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayResponse
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentProviderClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PayCryptoProvider : PayPaymentProviderClient {
    override fun createPayment(request: PayRequest): Mono<PayResponse> {
        TODO("Not yet implemented")
    }
}