package com.ocean.notificationservice.application

import com.ocean.notificationservice.infrastructure.persistence.JpaNotificationRepository
import com.ocean.notificationservice.infrastructure.persistence.NotificationEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class HandleOrderEventUseCase(
    private val notificationRepository: JpaNotificationRepository
) {

    @Transactional
    fun handle(orderId: UUID, eventType: String) {
        val notification = NotificationEntity(
            id = UUID.randomUUID(),
            orderId = orderId,
            type = eventType,
            createdAt = Instant.now()
        )
        notificationRepository.save(notification)
    }
}
