package com.ocean.userservice.application.port

import com.ocean.userservice.domain.User
import java.util.UUID

interface UserRepository {
    fun findByEmail(email: String): User?
    fun save(user: User): User
    fun findById(id: UUID): User?
}
