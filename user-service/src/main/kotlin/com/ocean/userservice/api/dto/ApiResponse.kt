package com.ocean.userservice.api.dto

import com.ocean.userservice.api.ApiError
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.media.Schema

@Hidden
@Schema(hidden = true)
data class ApiResponse<T>(
    val status: String,
    val message: String,
    val errors: List<ApiError> = emptyList(),
    val data: T? = null
)
