package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.domain.PayRequestNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono

@RestControllerAdvice
class PayApiExceptionHandler {

    @ExceptionHandler(PayRequestNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: PayRequestNotFoundException): Mono<Map<String,String>> =
        Mono.just(mapOf("error" to (ex.message ?: "Not found")))

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleOther(ex: Exception): Mono<Map<String,String>> =
        Mono.just(mapOf("error" to (ex.message ?: "An unexpected error occurred")))

}