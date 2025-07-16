package com.devspace.pagos360.paymentrequests.application.dto

import com.devspace.pagos360.paymentrequests.domain.PayDueDate
import com.devspace.pagos360.paymentrequests.domain.PayMoney
import com.devspace.pagos360.paymentrequests.domain.PayRequest
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

data class PayCreateRequestCmd(
    val description: String?,
    val firstDueDate: String?,
    val firstTotal: String?,
    val payerName: String?
) {
    fun toDomain(): PayRequest {
        require(!description.isNullOrBlank()) { "Description must not be null or blank" }
        require(!firstDueDate.isNullOrBlank()) { "First due date must not be null or blank" }
        require(!firstTotal.isNullOrBlank()) { "First total must not be null or blank" }
        require(!payerName.isNullOrBlank()) { "Payer name must not be null or blank" }

        val total = try {
            BigDecimal(firstTotal)
        } catch (e: Exception) {
            throw IllegalArgumentException("First total must be a valid number")
        }
        require(total > BigDecimal.ZERO) { "First total must be greater than zero" }

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val dueDate = try {
            LocalDate.parse(firstDueDate, formatter)
        } catch (e: Exception) {
            throw IllegalArgumentException("First due date must be a valid date in format dd-MM-yyyy")
        }
        require(!dueDate.isBefore(LocalDate.now())) { "First due date must be today or later" }

        return PayRequest(
            description = description,
            firstDueDate = PayDueDate(dueDate),
            firstTotal = PayMoney(total),
            payerName = payerName,
            id = UUID.randomUUID()
        )
    }
}