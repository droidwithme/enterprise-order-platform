package com.ocean.orderservice.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.Instant
import java.util.*

@Entity
@Table(name = "outbox_events")
class OutboxEventEntity(

    @Id
    val id: UUID,

    @Column(name = "aggregate_type", nullable = false)
    val aggregateType: String,

    @Column(name = "aggregate_id", nullable = false)
    val aggregateId: UUID,

    @Column(name = "event_type", nullable = false)
    val eventType: String,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    val payload: String,

    @Column(nullable = false)
    var status: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: Instant
) {

    protected constructor() : this(
        UUID.randomUUID(),
        "",
        UUID.randomUUID(),
        "",
        "",
        "NEW",
        Instant.now()
    )
}