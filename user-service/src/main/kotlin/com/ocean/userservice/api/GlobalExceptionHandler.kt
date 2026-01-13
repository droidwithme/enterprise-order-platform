package com.ocean.userservice.api

import com.ocean.userservice.api.dto.ApiResponse
import com.ocean.userservice.domain.exception.InvalidCredentialsException
import com.ocean.userservice.domain.exception.UserNotFoundException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.security.SignatureException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ApiResponse(
                status = "FAILED",
                message = "User not found",
                errors = listOf(ApiError("USER_NOT_FOUND"))
            )
        )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentials(ex: InvalidCredentialsException): ResponseEntity<ApiResponse<Nothing>> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            ApiResponse(
                status = "FAILED",
                message = "Invalid credentials",
                errors = listOf(ApiError("INVALID_CREDENTIALS"))
            )
        )
    }


    @ExceptionHandler(ExpiredJwtException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleExpiredJwt(ex: ExpiredJwtException): ApiResponse<Nothing> {
        return ApiResponse(
            status = "ERROR",
            message = "JWT expired",
            errors = listOf(ApiError("Token has expired")),
            data = null
        )
    }

    @ExceptionHandler(MalformedJwtException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleMalformedJwt(ex: MalformedJwtException): ApiResponse<Nothing> {
        return ApiResponse(
            status = "ERROR",
            message = "Invalid token",
            errors = listOf(ApiError("Malformed JWT")),
            data = null
        )
    }

    @ExceptionHandler(SignatureException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleInvalidSignature(ex: SignatureException): ApiResponse<Nothing> {
        return ApiResponse(
            status = "ERROR",
            message = "Invalid token signature",
            errors = listOf(ApiError("JWT signature mismatch")),
            data = null
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleGeneric(ex: Exception): ApiResponse<Nothing> {
        return ApiResponse(
            status = "ERROR",
            message = "Internal Server Error",
            errors = listOf(ApiError(ex.message ?: "Unknown error")),
            data = null
        )
    }
}
