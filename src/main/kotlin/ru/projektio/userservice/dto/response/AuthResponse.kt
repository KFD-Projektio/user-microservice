package ru.projektio.userservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @field:JsonProperty("access_token")
    val accessToken: String,
    @field:JsonProperty("refresh_token")
    val refreshToken: String,
    @field:JsonProperty("token_type")
    val tokenType: String = "Bearer",
    @field:JsonProperty("user_id")
    val userId: Long,
    val email: String,
)