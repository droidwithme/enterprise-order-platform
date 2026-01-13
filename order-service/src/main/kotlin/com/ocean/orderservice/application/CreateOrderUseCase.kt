package com.ocean.orderservice.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.UserId
import com.ocean.orderservice.domain.event.OrderCreatedEvent
import com.ocean.orderservice.infrastructure.persistence.JpaOutboxRepository
import com.ocean.orderservice.infrastructure.persistence.OutboxEventEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class CreateOrderUseCase(
    private val orderRepository: OrderRepository,
    private val jpaOutboxRepository: JpaOutboxRepository,
    private val objectMapper: ObjectMapper
) {

    @Transactional
    fun create(userId: UUID): Order {
        val order = Order.create(userId = UserId(userId))
        val saved = orderRepository.save(order)

        val event = OrderCreatedEvent(orderId = saved.id.value, userId = userId, occurredAt = Instant.now())

        jpaOutboxRepository.save(
            OutboxEventEntity(
                id = UUID.randomUUID(),
                aggregateType = "ORDER",
                aggregateId = saved.id.value,
                eventType = "ORDER_CREATED",
                payload = objectMapper.writeValueAsString(event),
                status = "NEW",
                createdAt = Instant.now()
            )
        )
        return saved

    }
}
