package com.ocean.orderservice.application.port

import com.ocean.orderservice.domain.event.*

interface OrderEventPublisher {
    fun publish(event: OrderCreatedEvent)
    fun publish(event: OrderApprovedEvent)
    fun publish(event: OrderCancelledEvent)
}
