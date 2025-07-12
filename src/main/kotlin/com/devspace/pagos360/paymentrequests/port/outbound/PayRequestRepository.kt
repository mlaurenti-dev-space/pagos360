package com.devspace.pagos360.paymentrequests.port.outbound

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Interface defining the repository for managing payment requests.
 * Provides methods to save, find by ID, and list payment requests.
 */
interface PayRequestRepository {
    fun save(request: PayRequest): Mono<PayRequest>
    fun findById(id: Any): Mono<PayRequest>
    fun list(page: Int, size: Int): Flux<PayRequest>
}