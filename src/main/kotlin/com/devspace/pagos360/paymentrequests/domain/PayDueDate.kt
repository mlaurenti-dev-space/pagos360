package com.devspace.pagos360.paymentrequests.domain

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Represents a due date for a payment request.
 *
 * @property date The due date and time, which must be in the future.
 * @throws IllegalArgumentException if the date is not in the future.
 */
data class PayDueDate(val date: LocalDate) {
    init {
        require(date.isAfter(LocalDate.now())) {
            "Due date must be in the future"
        }
    }
}