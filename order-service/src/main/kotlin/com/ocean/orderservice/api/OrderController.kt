package com.ocean.orderservice.api

import com.ocean.orderservice.application.ApproveOrderUseCase
import com.ocean.orderservice.application.CancelOrderUseCase
import com.ocean.orderservice.application.CreateOrderUseCase
import com.ocean.orderservice.application.GetOrderUseCase
import com.ocean.orderservice.application.GetOrdersByUserUseCase
import com.ocean.orderservice.domain.OrderId
import com.ocean.orderservice.domain.OrderStatus
import com.ocean.orderservice.domain.UserId
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("api/v1/orders")
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val approveOrderUseCase: ApproveOrderUseCase,
    private val cancelOrderUseCase: CancelOrderUseCase,
    private val getOrdersByUserUseCase: GetOrdersByUserUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(userId: UUID): CreateOrderResponse {
        val order = createOrderUseCase.create(userId = userId)
        return CreateOrderResponse(
            orderId = order.id.value,
            status = order.status.name
        )
    }

    @GetMapping("/{id}")
    fun getOrder(@PathVariable id: String): CreateOrderResponse {
        val order = getOrderUseCase.getById(OrderId.from(id))

        return CreateOrderResponse(
            orderId = order.id.value,
            status = order.status.name
        )
    }

    @PutMapping("/{id}/approve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun approveOrder(@PathVariable id: String): ApproveOrderResponse {
        approveOrderUseCase.approve(OrderId.from(id))
        return ApproveOrderResponse(status = OrderStatus.APPROVED.name)
    }


    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun cancelOrder(@PathVariable id: String): CancelOrderResponse {
        cancelOrderUseCase.cancel(OrderId.from(id))
        return CancelOrderResponse(status = OrderStatus.CANCELLED.name)
    }

    @GetMapping("/user/{userId}")
    fun getOrderByUser(@PathVariable userId: UserId): List<OrderResponse> {
        val listOfOrder = getOrdersByUserUseCase.getOrders(userId = userId.value.toString()) ?: emptyList()
        return listOfOrder.map {
            OrderResponse(
                orderId = it.id.value,
                status = it.status.name,
                createdAt = it.createdAt,
            )
        }
    }

}
