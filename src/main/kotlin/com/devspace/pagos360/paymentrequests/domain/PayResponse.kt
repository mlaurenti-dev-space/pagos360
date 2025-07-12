package com.devspace.pagos360.paymentrequests.domain

/**
 * Represents the response from the payment provider after initiating a checkout.
 *
 * @property requestId The unique identifier for the payment request.
 * @property checkoutUrl The URL to redirect the user for completing the payment.
 */
data class PayResponse(val requestId: Any, val checkoutUrl: String)