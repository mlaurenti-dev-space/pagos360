package com.devspace.pagos360.paymentrequests.infrastructure.adapter.pagos360

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PayPagos360RequestDetail private constructor(
    @JsonProperty("description")
    val description: String,
    @JsonProperty("first_due_date")
    val firstDueDate: String,
    @JsonProperty("first_total")
    val firstTotal: String,
    @JsonProperty("payer_name")
    val payerName: String
) {
    companion object {
        private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

        fun from(
            description: String,
            firstDueDate: LocalDate,
            firstTotal: String,
            payerName: String
        ): PayPagos360RequestDetail {
            require(description.isNotBlank()) { "description must not be empty" }
            require(firstTotal.isNotBlank()) { "firstTotal must not be empty" }
            require(payerName.isNotBlank()) { "payerName must not be empty" }

            val formattedDate = firstDueDate.format(formatter)
            return PayPagos360RequestDetail(
                description = description,
                firstDueDate = formattedDate,
                firstTotal = firstTotal,
                payerName = payerName
            )
        }
    }
}

data class PayPagos360RequestBody(
    @JsonProperty("payment_request")
    val paymentRequest: PayPagos360RequestDetail
)