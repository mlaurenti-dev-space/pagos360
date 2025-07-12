package com.devspace.pagos360.paymentrequests.port.outbound

/**
 * Represents the response from the payment provider after initiating a checkout.
 *
 * @property requestId The unique identifier for the payment request.
 * @property checkoutUrl The URL to redirect the user for completing the payment.
 */
data class PayCheckoutResponse(val requestId: Any, val checkoutUrl: String)
