package com.ocean.orderservice.domain

import com.ocean.orderservice.domain.exception.InvalidOrderStateException
import java.time.Instant
import java.util.UUID

class Order private constructor(
    val id: OrderId,
    val userId: UserId,
    val status: OrderStatus,
    val createdAt: Instant,
) {
    companion object {
        fun create(userId: UserId): Order {
            return Order(
                id = OrderId(UUID.randomUUID()),
                userId = userId,
                status = OrderStatus.CREATED,
                createdAt = Instant.now()
            )
        }

        fun fromPersistence(
            id: OrderId,
            userId: UserId,
            status: OrderStatus,
            createdAt: Instant
        ): Order {
            return Order(
                userId = userId,
                id = id,
                status = status,
                createdAt = createdAt
            )
        }
    }

    fun approve(): Order {
        if (status == OrderStatus.APPROVED) {
            throw InvalidOrderStateException("Order is already approved")
        }
        if (status != OrderStatus.CREATED) {
            throw InvalidOrderStateException("Order cannot be approved from state $status")
        }
        return fromPersistence(id = id, userId = userId, status = OrderStatus.APPROVED, createdAt = createdAt)
    }

    fun cancel(): Order {
        if (status == OrderStatus.CANCELLED) {
            throw InvalidOrderStateException("Order is already cancelled")
        }
        return fromPersistence(id = id, userId = userId, OrderStatus.CANCELLED, createdAt = createdAt)
    }

    fun restore(
        id: OrderId,
        userId: UserId,
        status: OrderStatus,
        createdAt: Instant
    ): Order {
        return fromPersistence(id = id, userId = userId, status = status, createdAt = createdAt)
    }
}
