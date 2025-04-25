package ru.projektio.userservice.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.dto.response.TestResponse
import ru.projektio.userservice.service.AuthService

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

    @PostMapping("/logout")
    fun logout(@RequestBody request: RefreshRequest): ResponseEntity<Map<String, String>> {
        authService.logout(request.refreshToken)
        return ResponseEntity.ok(mapOf("status" to "ok"))
    }
}