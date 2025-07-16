package com.devspace.pagos360.paymentrequests.application.dto

import java.math.BigDecimal

data class PayResponseDto(
    val id: Any,
    val description: String,
    val firstDueDate: String,
    val firstTotal: BigDecimal,
    val payerName: String,
    val status: String,
    val checkoutUrl: String? = null
)
