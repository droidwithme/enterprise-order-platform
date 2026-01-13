package com.ocean.userservice.infrastructure

import com.ocean.userservice.application.OrderHistoryUseCase
import com.ocean.userservice.domain.UserOrder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service("orderServiceClient")
class OrderServiceClient(
    private val restTemplate: RestTemplate
) : OrderHistoryUseCase {

    override fun getOrdersForUser(userId: String): List<UserOrder> {
        val url = "http://order-service:8081/api/v1/orders/user/$userId"
        return restTemplate
            .getForObject(url, Array<UserOrder>::class.java)
            ?.toList()
            ?: emptyList()
    }
}

