package ru.projektio.userservice.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.dto.response.TestResponse
import ru.projektio.userservice.service.AuthService
import ru.projektio.userservice.service.UserService

// /api/v1/auth/login

@RestController
@RequestMapping("/users/v1/auth")
class AuthController(
    private val userService: UserService,
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> { TODO("Finish") }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> { TODO("Finish") }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<AuthResponse> { TODO("Finish") }

    @GetMapping("/check")
    fun check(): ResponseEntity<TestResponse> { return ResponseEntity.ok( TestResponse("Hello world!"))
    }
}