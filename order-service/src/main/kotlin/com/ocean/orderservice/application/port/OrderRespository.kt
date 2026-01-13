package com.ocean.orderservice.application.port

import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.OrderId

interface OrderRepository {

    fun save(order: Order): Order

    fun findById(id: OrderId): Order?

    fun findByUserId(userId: String): List<Order>

}
