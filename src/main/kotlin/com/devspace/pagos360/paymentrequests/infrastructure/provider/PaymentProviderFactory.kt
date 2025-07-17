package com.devspace.pagos360.paymentrequests.infrastructure.provider

import com.devspace.pagos360.paymentrequests.domain.PaymentMethodType
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentProviderClient
import com.devspace.pagos360.paymentrequests.infrastructure.provider.crypto.PayCryptoProvider
import com.devspace.pagos360.paymentrequests.infrastructure.provider.paymentrequest.PayPaymentRequestProvider
import org.springframework.stereotype.Component

@Component
class PaymentProviderFactory(
    paymentRequestProvider: PayPaymentRequestProvider,
    payCryptoProvider: PayCryptoProvider
) {
    private val providerMap = mapOf(
        PaymentMethodType.REQUEST to paymentRequestProvider,
        PaymentMethodType.CRYPTO  to payCryptoProvider,
    )
    fun getProvider(type: PaymentMethodType): PayPaymentProviderClient =
        providerMap[type] ?: error("No payment provider for $type")
}