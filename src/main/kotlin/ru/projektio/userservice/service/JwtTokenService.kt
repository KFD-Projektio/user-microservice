package ru.projektio.userservice.service

import org.springframework.http.ResponseEntity
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.dto.response.AuthResponse
import java.util.*

interface JwtTokenService {
    fun getUsernameFromToken(token: String): String
    fun validateToken(token: String): Boolean
    fun createTokenPair(user: UserEntity): Pair<String, String>
    fun isTokenNotExpired(token: String): Boolean
    fun getTokenExpiration(token: String): Date
}