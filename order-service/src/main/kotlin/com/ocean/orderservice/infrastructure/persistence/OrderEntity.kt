package com.ocean.orderservice.infrastructure.persistence

import com.ocean.orderservice.domain.OrderStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "orders")
class OrderEntity(

    @Id
    val id: UUID,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    val createdAt: Instant
) {
    // JPA requirement
    protected constructor() : this(
        UUID.randomUUID(),
        OrderStatus.CREATED,
        UUID.randomUUID(),
        Instant.now()
    )
}
