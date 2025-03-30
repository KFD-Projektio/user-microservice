package ru.projektio.userservice.dto.request

data class RegisterRequest(
    val login: String,
    val email: String,
    val password: String
)
