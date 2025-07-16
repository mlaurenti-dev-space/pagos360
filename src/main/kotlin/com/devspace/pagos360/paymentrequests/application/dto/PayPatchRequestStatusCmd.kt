package com.devspace.pagos360.paymentrequests.application.dto

import com.devspace.pagos360.paymentrequests.domain.PayStatus

class PayPatchRequestStatusCmd(val status: PayStatus) {
    fun toStatus(): PayStatus {
        return when (status.name.lowercase()) {
            "paid" -> PayStatus.PAID
            "reversed" -> PayStatus.REVERSED
            else -> throw IllegalArgumentException("Invalid status: $status")
        }
    }
}