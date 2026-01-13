package com.ocean.userservice.application

import com.ocean.userservice.application.port.UserRepository
import com.ocean.userservice.domain.exception.InvalidCredentialsException
import com.ocean.userservice.infrastructure.security.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUserUseCase(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) {

    fun login(email: String, password: String): String {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("User not found")

        if (!passwordEncoder.matches(password, user.password)) {
            throw InvalidCredentialsException("Invalid credentials")
        }

        return jwtProvider.generateToken(user.id!!, user.email)
    }
}
