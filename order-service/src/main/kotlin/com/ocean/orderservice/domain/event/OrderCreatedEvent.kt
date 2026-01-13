package com.ocean.orderservice.domain.event

import java.time.Instant
import java.util.UUID

data class OrderCreatedEvent(
    val orderId: UUID,
    val userId: UUID,
    val occurredAt: Instant
)
