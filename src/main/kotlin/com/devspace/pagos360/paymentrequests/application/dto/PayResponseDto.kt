package com.devspace.pagos360.paymentrequests.application.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class PayResponseDto(
    val id: Any,
    val description: String,
    val firstDueDate: LocalDateTime,
    val firstTotal: BigDecimal,
    val payerName: String,
    val status: String,
    val checkoutUrl: String? = null
)
