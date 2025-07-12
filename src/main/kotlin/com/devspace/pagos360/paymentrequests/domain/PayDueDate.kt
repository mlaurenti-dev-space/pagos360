package com.devspace.pagos360.paymentrequests.domain

import java.time.LocalDateTime

/**
 * Represents a due date for a payment request.
 *
 * @property date The due date and time, which must be in the future.
 * @throws IllegalArgumentException if the date is not in the future.
 */
data class PayDueDate(val date: LocalDateTime) {
    init {
        require(date.isAfter(LocalDateTime.now().minusSeconds(1))) {
            "Due date must be in the future"
        }
    }
}