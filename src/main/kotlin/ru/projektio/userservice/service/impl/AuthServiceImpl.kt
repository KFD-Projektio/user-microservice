package ru.projektio.userservice.service.impl

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.service.AuthService
import ru.projektio.userservice.service.JwtTokenService

@Service
class AuthServiceImpl : AuthService {
    override fun authenticate(request: LoginRequest): ResponseEntity<AuthResponse> {
        TODO("Not yet implemented")
    }

    override fun register(request: RegisterRequest): ResponseEntity<AuthResponse> {
        TODO("Not yet implemented")
    }

    override fun refresh(request: RefreshRequest): ResponseEntity<AuthResponse> {
        TODO("Not yet implemented")
    }

}