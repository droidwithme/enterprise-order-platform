package com.ocean.userservice.api
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema

@Hidden
@Schema(hidden = true)
data class ApiError(
    val message: String
)
