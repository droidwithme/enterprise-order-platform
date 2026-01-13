package com.ocean.userservice.domain

import com.ocean.userservice.infrastructure.persistence.jpa.UserEntity
import java.time.Instant
import java.util.UUID

data class User(
    val id: UUID? = null,
    val email: String,
    val password: String,
    val createdAt: Instant
) {
    fun User.toEntity() = UserEntity(id, email, password, createdAt)
}