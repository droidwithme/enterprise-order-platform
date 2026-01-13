package com.ocean.orderservice.domain.event

import java.time.Instant
import java.util.UUID

data class OrderApprovedEvent(
    val orderId: UUID,
    val occurredAt: Instant
)
