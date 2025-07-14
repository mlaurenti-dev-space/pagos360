package com.devspace.pagos360.paymentrequests.application.dto

data class PayCreateRequestCmd(
    val description: String?,
    val firstDueDate: String?,
    val firstTotal: String?,
    val payerName: String?
)