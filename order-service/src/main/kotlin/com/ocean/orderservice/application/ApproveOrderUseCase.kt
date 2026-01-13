package com.ocean.orderservice.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.ocean.orderservice.application.port.OrderEventPublisher
import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.event.OrderApprovedEvent
import com.ocean.orderservice.domain.event.OrderCreatedEvent
import com.ocean.orderservice.domain.exception.OrderNotFoundException
import com.ocean.orderservice.infrastructure.persistence.JpaOutboxRepository
import com.ocean.orderservice.infrastructure.persistence.OutboxEventEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class ApproveOrderUseCase(
    private val orderRepository: OrderRepository,
    private val jpaOutboxRepository: JpaOutboxRepository,
    private val objectMapper: ObjectMapper
) {

    @Transactional
    fun approve(orderId: OrderId) {
        val order = orderRepository.findById(orderId)
            ?: throw OrderNotFoundException(orderId)

        val approved = order.approve()
        orderRepository.save(approved)

        val event =
            OrderCreatedEvent(orderId = approved.id.value, userId = approved.userId.value, occurredAt = Instant.now())

        jpaOutboxRepository.save(
            OutboxEventEntity(
                id = UUID.randomUUID(),
                aggregateType = "ORDER",
                aggregateId = approved.id.value,
                eventType = "ORDER_APPROVED",
                payload = objectMapper.writeValueAsString(event),
                status = "NEW",
                createdAt = Instant.now()
            )
        )
    }
}
