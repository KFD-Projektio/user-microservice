package ru.projektio.userservice.service

import org.springframework.http.ResponseEntity
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.dto.response.AuthResponse

interface JwtTokenService {
    fun validateToken(token: String): Boolean
    fun createTokenPair(user: UserEntity): Pair<String, String>
    fun getRefreshTokenExpiration(token: String): String
}