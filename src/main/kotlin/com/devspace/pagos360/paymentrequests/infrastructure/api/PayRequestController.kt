package com.devspace.pagos360.paymentrequests.infrastructure.api

import com.devspace.pagos360.paymentrequests.application.dto.PayCreateRequestCmd
import com.devspace.pagos360.paymentrequests.application.dto.PayResponseDto
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * Controller for managing payment requests.
 * Provides endpoints to create, list, and retrieve payment requests.
 *
 * @property payRequestUseCases Use cases for handling payment requests.
 */
@RestController
@RequestMapping("/api/payment-requests")
@Validated
@Tag(name = "Payment Requests", description = "Payment Requests")
class PayRequestController(private val payRequestUseCases: PayRequestUseCases) {

    @Operation(summary = "List Payment Requests")
    @GetMapping
    fun list(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Flux<PayResponseDto> =
        payRequestUseCases.listAll(page, size).map { it.toDto() }

    @Operation(summary = "Get Payment Request by ID")
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: String): Mono<PayResponseDto> =
        payRequestUseCases.findById(UUID.fromString(id)).map { it.toDto() }

    @Operation(summary = "Create Payment Requests")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody cmds: List<@Valid PayCreateRequestCmd>
    ): Flux<PayResponseDto> =
        payRequestUseCases.create(cmds.map { cmd ->
            cmd.toDomain()
        })
            .map { it.toDto() }
}
