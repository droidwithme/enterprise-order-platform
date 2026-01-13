package com.ocean.userservice.infrastructure.persistence.jpa

import com.ocean.userservice.domain.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val createdAt: Instant
) {

    protected constructor() : this(
        UUID.randomUUID(), "", "", Instant.now()
    )

    fun UserEntity.toDomain() = User(id!!, email, password, createdAt)
}