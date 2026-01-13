package com.ocean.userservice.infrastructure.service

import com.ocean.userservice.application.OrderHistoryUseCase
import com.ocean.userservice.application.ProfileUseCase
import com.ocean.userservice.application.port.UserRepository
import com.ocean.userservice.domain.UserProfile
import com.ocean.userservice.domain.exception.UserNotFoundException
import com.ocean.userservice.infrastructure.OrderServiceClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProfileUseCaseImpl(
    private val userRepository: UserRepository,
    @Qualifier("orderServiceClient")
    private val orderHistoryUseCase: OrderHistoryUseCase
) : ProfileUseCase {
    override fun getProfile(userId: String): UserProfile {
        val uuid = UUID.fromString(userId)
        val user = userRepository.findById(uuid)
            ?: throw UserNotFoundException(uuid)
        print("USER FOUND: $user")
        val orders = orderHistoryUseCase.getOrdersForUser(userId)

        return UserProfile(
            id = user.id.toString(),
            email = user.email,
            orders = orders
        )
    }
}
