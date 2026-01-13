package com.ocean.orderservice.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SpringDataOrderRepository : JpaRepository<OrderEntity, UUID> {
    fun findAllByUserId(userId: UUID): List<OrderEntity>
}