package com.ocean.userservice.infrastructure.persistence

import com.ocean.userservice.application.port.UserRepository
import com.ocean.userservice.domain.User
import com.ocean.userservice.domain.exception.UserNotFoundException
import com.ocean.userservice.infrastructure.JpaUserRepository
import com.ocean.userservice.infrastructure.persistence.jpa.UserEntity
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepositoryImpl(
    private val jpaRepo: JpaUserRepository
) : UserRepository {

    override fun save(user: User): User {
        val entity = UserEntity(
            email = user.email,
            password = user.password,
            createdAt = user.createdAt
        )
        return jpaRepo.save(entity).toDomain()
    }

    override fun findByEmail(email: String): User? =
        jpaRepo.findByEmail(email)?.toDomain()

    override fun findById(id: UUID): User? =
        jpaRepo.findById(id).orElse(null)?.toDomain()    ?: throw UserNotFoundException(id)

    private fun UserEntity.toDomain() =
        User(id!!, email, password, createdAt)
}
