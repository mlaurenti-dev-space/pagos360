package com.devspace.pagos360.paymentrequests.infrastructure.provider.paymentrequest

import com.fasterxml.jackson.annotation.JsonProperty

data class PayRequestResponse(@JsonProperty("checkout_url") val checkoutUrl: String)