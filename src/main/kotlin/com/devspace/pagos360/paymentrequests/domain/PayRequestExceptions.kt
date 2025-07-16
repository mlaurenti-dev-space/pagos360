package com.devspace.pagos360.paymentrequests.domain

class PayInvalidRequestException(message: String) : RuntimeException(message)

class PayRequestNotFoundException(id: Any) : RuntimeException("Payment Request with id=$id not found")