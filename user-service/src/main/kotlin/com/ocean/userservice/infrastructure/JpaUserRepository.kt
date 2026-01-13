package com.ocean.userservice.infrastructure

import com.ocean.userservice.infrastructure.persistence.jpa.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface JpaUserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
}