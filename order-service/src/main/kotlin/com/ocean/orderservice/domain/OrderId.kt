package com.ocean.orderservice.domain

import java.util.UUID

@JvmInline
value class OrderId(val value: UUID) {
    companion object {
        fun new(): OrderId = OrderId(UUID.randomUUID())

        fun from(raw: String): OrderId {
            return OrderId(UUID.fromString(raw))
        }


    }
}