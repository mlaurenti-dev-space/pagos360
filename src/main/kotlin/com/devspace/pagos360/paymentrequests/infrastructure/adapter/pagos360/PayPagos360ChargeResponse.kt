package com.devspace.pagos360.paymentrequests.infrastructure.adapter.pagos360

import com.fasterxml.jackson.annotation.JsonProperty

data class PayPagos360ChargeResponse(@JsonProperty("checkout_url") val checkoutUrl: String)