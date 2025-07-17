package com.devspace.pagos360.paymentrequests.domain

import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

typealias PayTransitionLogic = (PayRequest) -> PayRequest

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
    val checkoutUrl: String = StringUtils.EMPTY,
    @Transient val paymentMethod: PaymentMethodType = PaymentMethodType.REQUEST // Default payment method type
) {
    init {
        require(description.isNotBlank()) { "Description cannot be blank" }
        require(firstTotal.amount > BigDecimal.ZERO) { "Amount must be positive" }
    }

    fun markPaid(at: LocalDateTime = LocalDateTime.now()): PayRequest =
        copy(status = PayStatus.PAID, updatedAt = at)

    fun markReversed(at: LocalDateTime = LocalDateTime.now()): PayRequest =
        copy(status = PayStatus.REVERSED, updatedAt = at)

    fun transitionTo(target: PayStatus): PayRequest {
        val logic = transitions[this.status to target]
            ?: error("Transición inválida de ${this.status} a $target para PayRequest ${this.id}")
        return logic(this)
    }

    companion object {
        val transitions: Map<Pair<PayStatus, PayStatus>, PayTransitionLogic> = mapOf(
            (PayStatus.PENDING to PayStatus.PAID) to { pr ->
                pr.copy(status = PayStatus.PAID, updatedAt = LocalDateTime.now())
            },
            (PayStatus.PAID to PayStatus.REVERSED) to { pr ->
                pr.copy(status = PayStatus.REVERSED, updatedAt = LocalDateTime.now())
            }
            // Puedes agregar más transiciones aquí según sea necesario
        )
    }
}
