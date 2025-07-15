package com.devspace.pagos360.paymentrequests.application

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import com.devspace.pagos360.paymentrequests.domain.PayStatus
import com.devspace.pagos360.paymentrequests.domain.port.inbound.PayRequestUseCases
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayPaymentRequestProviderClient
import com.devspace.pagos360.paymentrequests.domain.port.outbound.PayRequestRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalDateTime
import java.util.UUID

@Service
class PayRequestService(
    private val payRequestRepository: PayRequestRepository,
    private val payPaymentRequestProviderClient: PayPaymentRequestProviderClient,
    txManager: ReactiveTransactionManager
) : PayRequestUseCases {

    private val logger: Logger = LoggerFactory.getLogger(PayRequestService::class.java)

    private val transactionalOperator = TransactionalOperator.create(txManager)

    override fun listAll(
        page: Int,
        size: Int
    ): Flux<PayRequest> = payRequestRepository.list(page, size)

    override fun findById(id: UUID): Mono<PayRequest> = payRequestRepository.findById(id)

    override fun create(requests: List<PayRequest>): Flux<PayRequest> {
        logger.info("Creating {} PayRequests", requests.size)
        return Flux.fromIterable(requests)
            .flatMapSequential({ pr ->
                payRequestRepository.save(pr)
                    .flatMap { saved ->
                        payPaymentRequestProviderClient.createPaymentRequest(saved)
                            .map { resp ->
                                saved.copy(
                                    updatedAt = LocalDateTime.now(),
                                    checkoutUrl = resp.checkoutUrl
                                )
                            }
                            .onErrorResume { ex ->
                                payRequestRepository.update(
                                    saved.copy(
                                        updatedAt = LocalDateTime.now(),
                                        status = PayStatus.ERROR
                                    )
                                )
                            }
                    }
                .retryWhen(Retry.fixedDelay(3, Duration.ofMinutes(1))) // si querés retries acá, o en createPaymentRequest
            }, 5)
    }


    override fun updateStatus(id: UUID, targetStatus: PayStatus): Mono<PayRequest> =
        payRequestRepository.findById(id)
            .map { it.transitionTo(targetStatus) }
            .flatMap {
                payRequestRepository.update(it)
            }
}