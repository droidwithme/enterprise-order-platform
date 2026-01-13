package com.ocean.orderservice.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaOutboxRepository : JpaRepository<OutboxEventEntity, UUID> {

    fun findTop10ByStatusOrderByCreatedAt(status: String): List<OutboxEventEntity>
}
