package com.devspace.pagos360.paymentrequests.infrastructure.r2dbc

import com.devspace.pagos360.paymentrequests.domain.PayDueDate
import com.devspace.pagos360.paymentrequests.domain.PayMoney
import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayStatus
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

/** * Represents a payment request entity for R2DBC persistence.
 *
 * @property id Unique identifier for the payment request.
 * @property description Description of the payment request.
 * @property firstDueDate The first due date for the payment.
 * @property firstTotal The total amount for the first payment.
 * @property payerName Name of the payer.
 * @property status Current status of the payment request.
 * @property createdAt Timestamp when the payment request was created.
 * @property updatedAt Timestamp when the payment request was last updated.
 */
@Table("pay_payment_requests")
class PayRequestEntity(
    @Id
    val id: UUID? = null,
    val description: String,
    val firstDueDate: LocalDateTime,
    val firstTotal: BigDecimal,
    val payerName: String,
    val status: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun fromDomain(domain: PayRequest): PayRequestEntity =
            PayRequestEntity(
                id            = domain.id,
                description   = domain.description,
                firstDueDate  = domain.firstDueDate.date,
                firstTotal    = domain.firstTotal.amount,
                payerName     = domain.payerName,
                status        = domain.status.name,
                createdAt     = domain.createdAt,
                updatedAt     = domain.updatedAt
            )
    }

    fun toDomain(): PayRequest =
        PayRequest(
            id            = this.id ?: throw IllegalStateException("ID must not be null"),
            description   = this.description,
            firstDueDate  = PayDueDate(this.firstDueDate),
            firstTotal    = PayMoney(this.firstTotal, "ARS"),
            payerName     = this.payerName,
            status        = PayStatus.valueOf(this.status),
            createdAt     = this.createdAt,
            updatedAt     = this.updatedAt
        )
}
