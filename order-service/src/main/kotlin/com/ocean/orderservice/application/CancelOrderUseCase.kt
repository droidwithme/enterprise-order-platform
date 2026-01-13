package com.ocean.orderservice.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.ocean.orderservice.application.port.OrderEventPublisher
import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.event.OrderCancelledEvent
import com.ocean.orderservice.domain.event.OrderCreatedEvent
import com.ocean.orderservice.domain.exception.OrderNotFoundException
import com.ocean.orderservice.infrastructure.persistence.JpaOutboxRepository
import com.ocean.orderservice.infrastructure.persistence.OutboxEventEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class CancelOrderUseCase(
    private val orderRepository: OrderRepository,
    private val jpaOutboxRepository: JpaOutboxRepository,
    private val objectMapper: ObjectMapper
) {
    @Transactional
    fun cancel(orderId: OrderId): Order? {
        val order = orderRepository.findById(orderId)
            ?: throw OrderNotFoundException(orderId)

        val cancelled = order.cancel()
        orderRepository.save(cancelled)
        val event = OrderCreatedEvent(
            orderId = cancelled.id.value,
            userId =  cancelled.userId.value ,
            Instant.now()
        )



        jpaOutboxRepository.save(
            OutboxEventEntity(
                id = UUID.randomUUID(),
                aggregateType = "ORDER",
                aggregateId = cancelled.id.value,
                eventType = "ORDER_CANCELLED",
                payload = objectMapper.writeValueAsString(event),
                status = "NEW",
                createdAt = Instant.now()
            )
        )

        return cancelled
    }
}