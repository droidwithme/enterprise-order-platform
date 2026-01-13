package com.ocean.orderservice.application

import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.exception.OrderNotFoundException
import org.springframework.stereotype.Service

@Service
class GetOrderUseCase(
    private val orderRepository: OrderRepository
) {

    fun getById(id: OrderId): Order {
        return orderRepository.findById(id)
            ?: throw OrderNotFoundException(id)
    }
}
