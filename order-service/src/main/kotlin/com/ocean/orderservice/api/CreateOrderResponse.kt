package com.ocean.orderservice.api

import java.util.UUID

data class CreateOrderResponse(
    val orderId: UUID,
    val status: String
)
