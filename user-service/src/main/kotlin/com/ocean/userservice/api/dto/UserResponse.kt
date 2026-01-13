package com.ocean.userservice.api.dto

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Hidden
@Schema(hidden = true)
data class UserResponse(
    val id: UUID,
    val email: String
)
