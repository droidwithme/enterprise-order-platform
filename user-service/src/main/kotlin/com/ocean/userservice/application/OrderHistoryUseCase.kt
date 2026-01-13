package com.ocean.userservice.application

import com.ocean.userservice.domain.UserOrder

interface OrderHistoryUseCase {
    fun getOrdersForUser(userId: String): List<UserOrder>
}