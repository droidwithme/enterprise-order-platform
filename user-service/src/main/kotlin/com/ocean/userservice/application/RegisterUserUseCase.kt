package com.ocean.userservice.application

import com.ocean.userservice.application.port.UserRepository
import com.ocean.userservice.domain.User
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun register(email: String, rawPassword: String): User {
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(rawPassword.length >= 6) { "Password too short" }

        if (userRepository.findByEmail(email) != null) {
            throw IllegalStateException("User already exists with email: $email")
        }

        val user = User(
            email = email,
            password = passwordEncoder.encode(rawPassword),
            createdAt = Instant.now()
        )


        return userRepository.save(user)
    }
}

