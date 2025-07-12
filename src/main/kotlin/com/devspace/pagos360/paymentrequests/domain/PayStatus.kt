package com.devspace.pagos360.paymentrequests.domain

/**
 * Represents the status of a payment request.
 *
 * @property PENDING The payment request is pending.
 * @property PAID The payment request has been paid.
 * @property REVERSED The payment request has been reversed.
 */
enum class PayStatus { PENDING, PAID, REVERSED }
