package ru.projektio.userservice.dto.response

data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val tokenType: String = "Bearer",
    val expiresIn: Long,
    val userId: Long,
    val email: String,
)
