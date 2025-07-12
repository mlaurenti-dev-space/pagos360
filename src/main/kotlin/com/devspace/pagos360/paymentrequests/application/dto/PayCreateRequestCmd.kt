package com.devspace.pagos360.paymentrequests.application.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class PayCreateRequestCmd(
    val description: String,
    val firstDueDate: LocalDateTime,
    val firstTotal: BigDecimal,
    val payerName: String
)
