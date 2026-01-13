package com.ocean.notificationservice.infrastructure.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "notifications")
class NotificationEntity(

    @Id
    val id: UUID?,

    @Column(nullable = false)
    val orderId: UUID?,

    @Column(nullable = false)
    val type: String?,

    @Column(nullable = false)
    val createdAt: Instant?
){
    // JPA requires this
    protected constructor() : this(null, null, null, null)
}
