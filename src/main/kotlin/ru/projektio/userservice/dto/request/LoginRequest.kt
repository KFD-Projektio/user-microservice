package ru.projektio.userservice.dto.request

data class LoginRequest(
    val login: String?,
    val email: String?,
    val passwordHash: String
)
