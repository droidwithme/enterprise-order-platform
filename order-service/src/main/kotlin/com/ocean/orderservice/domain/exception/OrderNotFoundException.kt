package com.ocean.orderservice.domain.exception

import com.ocean.orderservice.domain.OrderId

class OrderNotFoundException(orderId: OrderId) :
    RuntimeException("Order not found with id: ${orderId.value}")
