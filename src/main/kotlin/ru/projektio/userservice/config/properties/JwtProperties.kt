package ru.projektio.userservice.config.properties

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("jwt")
data class JwtProperties(
    val secret: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)
