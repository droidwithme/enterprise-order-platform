package com.ocean.orderservice.infrastructure.messaging

import com.fasterxml.jackson.databind.ObjectMapper
import com.ocean.orderservice.infrastructure.persistence.JpaOutboxRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class OutboxEventPublisher(
    private val outboxRepository: JpaOutboxRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {

    @Scheduled(fixedDelay = 5000)
    fun publish() {
        val events = outboxRepository.findTop10ByStatusOrderByCreatedAt("NEW")
        println("OUTBOX EVENTS FOUND = ${events.size}")
        events.forEach { event ->
            try {
                kafkaTemplate.send(
                    topicFor(event.eventType),
                    event.aggregateId.toString(),
                    objectMapper.readValue(event.payload, Any::class.java)
                ).whenComplete { result, ex ->
                    if (ex != null) {
                        println("FAILED TO SEND EVENT: ${ex.message}")
                    } else {
                        println("EVENT SENT TO KAFKA: ${result.recordMetadata.topic()}")
                    }
                }
                println("PUBLISHING EVENT ${event.eventType} TO KAFKA")
                event.status = "SENT"
                outboxRepository.save(event)
            } catch (ex: Exception) {
                println("ERROR PUBLISHING EVENT ${event.eventType} TO KAFKA")
            }
        }
    }

    private fun topicFor(eventType: String): String =
        when (eventType) {
            "ORDER_CREATED" -> "order.created"
            "ORDER_APPROVED" -> "order.approved"
            "ORDER_CANCELLED" -> "order.cancelled"
            else -> throw IllegalArgumentException("Unknown event type")
        }
}
