package com.ocean.notificationservice.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaNotificationRepository :
    JpaRepository<NotificationEntity, UUID>
