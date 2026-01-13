package com.ocean.orderservice.infrastructure.messaging

import com.ocean.orderservice.application.port.OrderEventPublisher
import com.ocean.orderservice.domain.event.*
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaOrderEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, Any>
) : OrderEventPublisher {

    override fun publish(event: OrderCreatedEvent) {
        kafkaTemplate.send("order.created", event.orderId.toString(), event)
    }

    override fun publish(event: OrderApprovedEvent) {
        kafkaTemplate.send("order.approved", event.orderId.toString(), event)
    }

    override fun publish(event: OrderCancelledEvent) {
        kafkaTemplate.send("order.cancelled", event.orderId.toString(), event)
    }
}
