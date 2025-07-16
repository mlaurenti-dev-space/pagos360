package com.devspace.pagos360.paymentrequests.domain

import java.math.BigDecimal

/**
 * Represents a monetary amount for payment requests.
 *
 * @property amount The amount of money, which must be non-negative.
 * @property currency The currency in ISO 4217 format, defaulting to "ARS".
 * @throws IllegalArgumentException if the amount is negative or if the currency is not a 3-letter ISO code.
 */
data class PayMoney(val amount: BigDecimal, val currency: String = "ARS") {
    init {
        require(amount >= BigDecimal.ZERO) { "Money amount must be non-negative" }
        require(currency.length == 3) { "Currency must be a 3-letter ISO code" }
    }

    /**
     * Adds another PayMoney instance to this one.
     *
     * @param other The PayMoney instance to add.
     * @return A new PayMoney instance with the combined amount.
     * @throws IllegalArgumentException if the currencies do not match.
     */
    operator fun plus(other: PayMoney): PayMoney {
        require(currency == other.currency) { "Cannot add different currencies" }
        return PayMoney(amount + other.amount, currency)
    }
}
