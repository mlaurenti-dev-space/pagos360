package com.devspace.pagos360.paymentrequests.domain.port.inbound

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayStatus
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 * Interface defining the use cases for managing payment requests.
 * Provides methods to list, find, create, and mark payment requests as paid.
 */
interface PayRequestUseCases {
    fun listAll(page: Int, size: Int): Flux<PayRequest>
    fun findById(id: UUID): Mono<PayRequest>
    fun create(requests: List<PayRequest>): Flux<PayRequest>
    fun updateStatus(id: UUID, targetStatus: PayStatus): Mono<PayRequest>
}