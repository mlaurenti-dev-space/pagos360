package com.devspace.pagos360.paymentrequests.application

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentRequestProviderClient
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayRequestRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalDateTime

@Service
class PayRequestService(
    private val payRequestRepository: PayRequestRepository,
    private val payPaymentRequestProviderClient: PayPaymentRequestProviderClient
) : PayRequestUseCases {

    override fun listAll(
        page: Int,
        size: Int
    ): Flux<PayRequest> = payRequestRepository.list(page, size)

    override fun findById(id: Any): Mono<PayRequest> = payRequestRepository.findById(id)

    override fun create(requests: List<PayRequest>): Flux<PayRequest> =
        Flux.fromIterable(requests)
            .flatMapSequential({ pr ->
                // 1. persistir en estado PENDING
                payRequestRepository.save(pr)
                    // 2. crear cargo en provider y obtener URL
                    .flatMap { saved ->
                        payPaymentRequestProviderClient.createPaymentRequest(saved)
                            .map { resp ->
                                // 3. actualizar con checkoutUrl, si quieres guardar el campo
                                saved.copy(updatedAt = LocalDateTime.now())
                            }
                    }
                    // 4. retry en errores externos
                    .retryWhen(Retry.fixedDelay(3, Duration.ofMinutes(1)))
            }, 5)   // hasta 5 concurrencias

    override fun markPaid(id: Any): Mono<PayRequest> =
        payRequestRepository.findById(id)
            .map { it.markPaid(LocalDateTime.now()) }
            .flatMap { payRequestRepository.save(it) }
}