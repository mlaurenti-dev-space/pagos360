package com.devspace.pagos360.paymentrequests.infrastructure.r2dbc

import com.devspace.pagos360.paymentrequests.domain.PayRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

/** * Repository interface for managing payment requests using Spring Data R2DBC.
 * Provides methods to perform CRUD operations and custom queries.
 */
interface PaySpringDataRequestRepository : ReactiveCrudRepository<PayRequestEntity, Any> {
    @Query("""
    SELECT * 
      FROM pay_payment_requests 
     ORDER BY created_at DESC 
     OFFSET :#{#pageable.offset} 
     LIMIT  :#{#pageable.pageSize}
  """)
    fun findAllBy(pageable: Pageable): Flux<PayRequestEntity>
}