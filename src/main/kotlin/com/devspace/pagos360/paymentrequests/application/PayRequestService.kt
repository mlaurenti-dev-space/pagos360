package com.devspace.pagos360.paymentrequests.application

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayStatus
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentRequestProviderClient
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayRequestRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.UUID

@Service
class PayRequestService(
    private val payRequestRepository: PayRequestRepository,
    private val payPaymentRequestProviderClient: PayPaymentRequestProviderClient
) : PayRequestUseCases {

    private val logger: Logger = LoggerFactory.getLogger(PayRequestService::class.java)

    override fun listAll(
        page: Int,
        size: Int
    ): Flux<PayRequest> = payRequestRepository.list(page, size)

    override fun findById(id: UUID): Mono<PayRequest> = payRequestRepository.findById(id)

    override fun create(requests: List<PayRequest>): Flux<PayRequest> {
        logger.info("Creating {} PayRequests", requests.size)
        return Flux.fromIterable(requests).doOnSubscribe { logger.info("Subscrito!") }
            .flatMapSequential({ pr ->
                logger.debug("Persisting PayRequest in PENDING state: {}", pr)
                // 1. persistir en estado PENDING
                payRequestRepository.save(pr)
                    // 2. crear cargo en provider y obtener URL
                    .flatMap { saved ->
                        logger.debug("Saved PayRequest: {}", saved)
                        payPaymentRequestProviderClient.createPaymentRequest(saved)
                            .map { resp ->
                                logger.debug("Received provider response for PayRequest id={}", saved.id)
                                // 3. actualizar con checkoutUrl, si quieres guardar el campo
                                saved.copy(updatedAt = LocalDateTime.now(), checkoutUrl = resp.checkoutUrl)
                            }
                    }
                // 4. retry en errores externos
                // .retryWhen(Retry.fixedDelay(3, Duration.ofMinutes(1)))
            }, 5).doOnError { e -> logger.error("Error in create PayRequests", e) }   // hasta 5 concurrencias
    }

    override fun updateStatus(id: UUID, targetStatus: PayStatus): Mono<PayRequest> =
        payRequestRepository.findById(id)
            .map { it.transitionTo(targetStatus) }
            .flatMap {
                payRequestRepository.update(it)
            }
}