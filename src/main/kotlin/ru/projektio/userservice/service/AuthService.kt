package ru.projektio.userservice.service

import org.springframework.http.ResponseEntity
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse

interface AuthService {
    fun authenticate(request: LoginRequest): AuthResponse
    fun register(request: RegisterRequest): AuthResponse
    fun refresh(request: RefreshRequest): AuthResponse
    fun logout(refreshToken: String)
}