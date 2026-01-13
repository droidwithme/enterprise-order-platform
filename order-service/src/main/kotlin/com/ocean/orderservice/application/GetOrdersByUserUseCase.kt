package com.ocean.orderservice.application

import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.UserId
import com.ocean.orderservice.domain.exception.OrderNotFoundException
import org.springframework.stereotype.Service

@Service
class GetOrdersByUserUseCase(
    private val orderRepository: OrderRepository
) {

    fun getOrders(userId: String): List<Order> =
        orderRepository.findByUserId(userId)
}

