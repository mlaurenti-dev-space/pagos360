package com.devspace.pagos360.paymentrequests.domain

import java.time.LocalDate

data class PayDueDate(val date: LocalDate) {
    init {
        /**
         * En el caso de necesitar validaciones adicionales, se pueden agregar aquí.
         */
    }
}