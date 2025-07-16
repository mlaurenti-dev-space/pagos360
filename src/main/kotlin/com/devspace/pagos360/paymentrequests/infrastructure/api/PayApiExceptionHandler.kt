package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.domain.PayRequestNotFoundException
import com.fasterxml.jackson.databind.exc.InvalidFormatException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono

@RestControllerAdvice
class PayApiExceptionHandler {

    @ExceptionHandler(PayRequestNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: PayRequestNotFoundException): Mono<Map<String, String>> =
        Mono.just(mapOf("error" to (ex.message ?: "Not found")))

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleOther(ex: Exception): Mono<Map<String, String>> =
        Mono.just(mapOf("error" to (ex.message ?: "An unexpected error occurred")))

    @ExceptionHandler(WebClientResponseException::class)
    fun handleWebClientResponseException(ex: WebClientResponseException): Mono<ResponseEntity<Map<String, Any>>> {
        return Mono.just(
            ResponseEntity.status(ex.statusCode).body(
                mapOf(
                    "error" to ex.responseBodyAsString
                )
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): Mono<Map<String, String>> =
        Mono.just(mapOf("error" to (ex.message ?: "An unexpected error occurred")))

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException): Mono<ResponseEntity<Map<String, String>>> =
        Mono.just(
            ResponseEntity.status(ex.statusCode).body(
                mapOf("error" to (ex.reason ?: "An unexpected error occurred"))
            )
        )
}