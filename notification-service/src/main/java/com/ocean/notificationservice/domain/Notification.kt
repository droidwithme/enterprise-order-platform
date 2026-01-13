package com.ocean.notificationservice.domain

import java.time.Instant
import java.util.UUID

data class Notification(
    val id: UUID,
    val orderId: UUID,
    val type: String,
    val createdAt: Instant
)
