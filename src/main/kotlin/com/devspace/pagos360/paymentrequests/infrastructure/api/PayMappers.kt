package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.application.dto.PayResponseDto
import com.devspace.pagos360.paymentrequests.domain.PayRequest

fun PayRequest.toDto(checkoutUrl: String? = null) = PayResponseDto(
    id            = this.id!!,
    description   = this.description,
    firstDueDate  = this.firstDueDate.date,
    firstTotal    = this.firstTotal.amount,
    payerName     = this.payerName,
    status        = this.status.name,
    checkoutUrl   = checkoutUrl
)


data class WebhookEvent(val id: String)