package ru.projektio.userservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource


@ConfigurationProperties("jwt")
data class JwtProperties(
    val publicRsaFile: String,
    val privateRsaFile: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
) {
//    val publicRsaKey: String by lazy { publicRsaFile.inputStream.bufferedReader().use { it.readText() } }
//    val privateRsaKey: String by lazy { privateRsaFile.inputStream.bufferedReader().use { it.readText() } }
}
