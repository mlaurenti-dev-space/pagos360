package com.devspace.pagos360.paymentrequests.domain.port.inbound

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Interface defining the use cases for managing payment requests.
 * Provides methods to list, find, create, and mark payment requests as paid.
 */
interface PayRequestUseCases {
    fun listAll(page: Int, size: Int): Flux<PayRequest>
    fun findById(id: Any): Mono<PayRequest>
    fun create(requests: List<PayRequest>): Flux<PayRequest>
    fun markPaid(id: Any): Mono<PayRequest>
}