package com.ocean.orderservice.api

import com.ocean.orderservice.domain.exception.InvalidOrderStateException
import com.ocean.orderservice.domain.exception.OrderNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException::class)
    fun handleNotFound(ex: OrderNotFoundException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(ex.message!!),
            HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(InvalidOrderStateException::class)
    fun handleInvalidState(ex: InvalidOrderStateException): ResponseEntity<ApiError> {
        return ResponseEntity(
            ApiError(ex.message!!),
            HttpStatus.CONFLICT
        )
    }
}
