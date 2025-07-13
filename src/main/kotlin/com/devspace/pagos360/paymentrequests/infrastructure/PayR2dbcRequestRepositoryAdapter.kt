package com.devspace.pagos360.paymentrequests.infrastructure

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayRequestRepository
import com.devspace.pagos360.paymentrequests.infrastructure.r2dbc.PayRequestEntity
import com.devspace.pagos360.paymentrequests.infrastructure.r2dbc.PaySpringDataRequestRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class PayR2dbcRequestRepositoryAdapter(
    private val delegate: PaySpringDataRequestRepository
) : PayRequestRepository {

    override fun save(request: PayRequest): Mono<PayRequest> =
        delegate.save(PayRequestEntity.fromDomain(request, true)).map { it.toDomain() }

    override fun findById(id: Any): Mono<PayRequest> = delegate.findById(id).map { it.toDomain() }

    override fun list(page: Int, size: Int): Flux<PayRequest> =
        delegate.findAllBy(PageRequest.of(page, size)).map { it.toDomain() }
}