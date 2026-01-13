package com.ocean.userservice.api.controller

import com.ocean.userservice.api.dto.ApiResponse
import com.ocean.userservice.api.dto.UserResponse
import com.ocean.userservice.application.ProfileUseCase
import com.ocean.userservice.application.port.UserRepository
import com.ocean.userservice.domain.UserProfile
import com.ocean.userservice.domain.exception.UserNotFoundException
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/profile")
class ProfileController(
    private val profileUseCase: ProfileUseCase
) {

    @GetMapping
    fun profile(authentication: Authentication): ApiResponse<UserProfile> {
        val profile = profileUseCase.getProfile(authentication.name)

        return ApiResponse(
            status = "SUCCESS",
            message = "Profile fetched",
            data = profile
        )
    }
}