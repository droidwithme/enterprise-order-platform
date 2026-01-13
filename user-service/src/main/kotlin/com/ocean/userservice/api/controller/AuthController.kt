package com.ocean.userservice.api.controller

import com.ocean.userservice.api.dto.ApiResponse
import com.ocean.userservice.application.LoginUserUseCase
import com.ocean.userservice.application.RegisterUserUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val registerUser: RegisterUserUseCase, private val loginUser: LoginUserUseCase
) {

    @PostMapping("/register")
    fun register(@RequestBody req: RegisterRequest): ApiResponse<RegisterResponse> {
        val user = registerUser.register(req.email, req.password)
        return ApiResponse(
            status = "SUCCESS",
            message = "User registered successfully",
            data = RegisterResponse(user.id.toString(), user.email)

        )
    }


    @PostMapping("/login")
    fun login(@RequestBody req: LoginRequest): ApiResponse<LoginResponse> {
        val token = loginUser.login(req.email, req.password)
        return ApiResponse(
            status = "SUCCESS",
            message = "Login successful",
            data = LoginResponse(token)
        )
    }

    data class LoginResponse(val token: String)
    data class LoginRequest(val email: String, val password: String)
    data class RegisterRequest(val email: String, val password: String)
    data class RegisterResponse(val id: String, val email: String)

}