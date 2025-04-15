package ru.projektio.userservice.controllers

import org.springframework.http.HttpStatusCode
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
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.authenticate(request))

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.status(201).body(authService.register(request))

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.refresh(request))

    @GetMapping("/check")
    fun check(): ResponseEntity<TestResponse> { return ResponseEntity.ok(TestResponse("Hello world!"))
    }
}