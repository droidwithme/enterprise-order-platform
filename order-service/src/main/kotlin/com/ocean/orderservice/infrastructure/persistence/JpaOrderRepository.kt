package com.ocean.orderservice.infrastructure.persistence

import com.ocean.orderservice.application.port.OrderRepository
import com.ocean.orderservice.domain.Order
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.OrderStatus
import com.ocean.orderservice.domain.UserId
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class JpaOrderRepository(
    private val springDataRepository: SpringDataOrderRepository
) : OrderRepository {

    override fun save(order: Order): Order {
        val entity = OrderEntity(
            id = order.id.value,
            userId = order.userId.value,
            status = order.status,
            createdAt = order.createdAt
        )
        springDataRepository.save(entity)
        return order
    }

    override fun findById(id: OrderId): Order? {
        return springDataRepository.findById(id.value)
            .map {
                Order.fromPersistence(
                    id = OrderId(it.id),
                    userId = UserId(it.userId),
                    status = OrderStatus.valueOf(it.status.name),
                    createdAt = it.createdAt
                )
            }
            .orElse(null)
    }

    override fun findByUserId(userId: String): List<Order> {
        val uuid = UUID.fromString(userId)

        return springDataRepository.findAllByUserId(uuid)
            .map {
                Order.fromPersistence(
                    id = OrderId(it.id),
                    userId = UserId(it.userId),
                    status = OrderStatus.valueOf(it.status.name),
                    createdAt = it.createdAt
                )
            }
    }

}