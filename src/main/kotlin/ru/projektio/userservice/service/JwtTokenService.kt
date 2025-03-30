package ru.projektio.userservice.service

import org.springframework.http.ResponseEntity
import ru.projektio.userservice.dto.response.AuthResponse

interface JwtTokenService {
    fun validateToken(token: String): Boolean
    fun createRefreshToken(token: String): String
    fun createAccessToken(token: String): String

    fun getRefreshTokenExpiration(token: String): String
}