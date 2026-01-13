package com.ocean.userservice.domain

data class UserProfile(
    val id: String,
    val email: String,
    val orders: List<UserOrder>
)

data class UserOrder(
    val orderId: String,
    val status: String,
    val createdAt: String
)
