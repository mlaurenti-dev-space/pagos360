package com.devspace.pagos360.paymentrequests.domain

import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/** * Represents a payment request in the system.
 *
 * @property id Unique identifier for the payment request.
 * @property description Description of the payment request.
 * @property firstDueDate The first due date for the payment.
 * @property firstTotal The total amount due for the payment.
 * @property payerName Name of the person or entity responsible for the payment.
 * @property status Current status of the payment request, defaulting to PENDING.
 * @property createdAt Timestamp when the payment request was created.
 * @property updatedAt Timestamp when the payment request was last updated.
 */
data class PayRequest(
    val id: UUID,
    val description: String,
    val firstDueDate: PayDueDate,
    val firstTotal: PayMoney,
    val payerName: String,
    val status: PayStatus = PayStatus.PENDING,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val checkoutUrl: String = StringUtils.EMPTY
) {
    init {
        require(description.isNotBlank()) { "Description cannot be blank" }
        require(firstTotal.amount > BigDecimal.ZERO) { "Amount must be positive" }
    }

    fun markPaid(at: LocalDateTime = LocalDateTime.now()): PayRequest =
        copy(status = PayStatus.PAID, updatedAt = at)

    fun markReversed(at: LocalDateTime = LocalDateTime.now()): PayRequest =
        copy(status = PayStatus.REVERSED, updatedAt = at)
}
