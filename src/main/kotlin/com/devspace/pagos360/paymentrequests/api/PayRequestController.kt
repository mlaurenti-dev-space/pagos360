package com.devspace.pagos360.paymentrequests.api

import com.devspace.pagos360.paymentrequests.application.dto.PayCreateRequestCmd
import com.devspace.pagos360.paymentrequests.application.dto.PayResponseDto
import com.devspace.pagos360.paymentrequests.domain.PayDueDate
import com.devspace.pagos360.paymentrequests.domain.PayMoney
import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/api/payments")
class PayRequestController(private val payRequestUseCases: PayRequestUseCases) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @RequestBody cmds: List<PayCreateRequestCmd>
    ): Flux<PayResponseDto> =
        payRequestUseCases.create(cmds.map { cmd ->
            PayRequest(
                description  = cmd.description,
                firstDueDate = PayDueDate(cmd.firstDueDate),
                firstTotal   = PayMoney(cmd.firstTotal, "ARS"),
                payerName    = cmd.payerName
            )
        })
            .map { it.toDto() }
}