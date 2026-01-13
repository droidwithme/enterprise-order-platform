package com.ocean.orderservice.api


import java.time.Instant
import java.util.UUID

data class OrderResponse(
    val orderId: UUID,
    val status: String,
    val createdAt: Instant
)
