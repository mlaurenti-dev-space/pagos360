package com.devspace.pagos360.paymentrequests.application.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.apache.commons.lang3.StringUtils
import java.math.BigDecimal
import java.time.LocalDateTime

data class PayCreateRequestCmd(
    @field:NotBlank(message = "Description cannot be blank")
    val description: String? = StringUtils.EMPTY,
    @field:NotNull(message = "First due date cannot be null")
    @field:FutureOrPresent(message = "First due date must be today or in the future")
    val firstDueDate: LocalDateTime? = null,
    @field:NotNull(message = "First total cannot be null")
    @field:DecimalMin("0.0", inclusive = false)
    val firstTotal: BigDecimal? = null,
    @field:NotBlank(message = "Payer name cannot be blank")
    val payerName: String? = StringUtils.EMPTY
)
