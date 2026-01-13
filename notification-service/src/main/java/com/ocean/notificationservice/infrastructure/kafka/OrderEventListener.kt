package com.ocean.notificationservice.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.ocean.notificationservice.application.HandleOrderEventUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class OrderEventListener(
    private val useCase: HandleOrderEventUseCase,
    private val objectMapper: ObjectMapper
) {

    @KafkaListener(
        topics = ["order.created", "order.approved", "order.cancelled"],
        groupId = "notification-service"
    )
    fun consume(
        message: String,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String
    ) {
        try {
            println("RAW MESSAGE RECEIVED FROM $topic: $message")

            val node = objectMapper.readTree(message)
            val orderId = UUID.fromString(node["orderId"].asText())

            val eventType = when (topic) {
                "order.created" -> "ORDER_CREATED"
                "order.approved" -> "ORDER_APPROVED"
                "order.cancelled" -> "ORDER_CANCELLED"
                else -> "UNKNOWN"
            }

            useCase.handle(orderId, eventType)

            println("Notification persisted for $orderId [$eventType]")
        } catch (ex: Exception) {
            println("Failed to process message from $topic: ${ex.message}")
            ex.printStackTrace()
        }
    }
}
