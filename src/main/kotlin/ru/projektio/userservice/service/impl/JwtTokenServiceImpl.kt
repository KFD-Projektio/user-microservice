package ru.projektio.userservice.service.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import ru.projektio.userservice.config.properties.JwtProperties
import ru.projektio.userservice.database.entity.RefreshTokenEntity
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.database.repository.RefreshTokenDao
import ru.projektio.userservice.service.JwtTokenService
import java.util.*

class JwtTokenServiceImpl(
    private val jwtProperties: JwtProperties,
    private val refreshTokenDao: RefreshTokenDao
) : JwtTokenService {

    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    override fun validateToken(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun createTokenPair(user: UserEntity): Pair<String, String> {
        val accessToken = createToken(user, getAccessTokenExpirationDate())

        val refreshTokenExpiration = getRefreshTokenExpirationDate()
        val refreshToken = createToken(user, refreshTokenExpiration)


        refreshTokenDao.save(
            RefreshTokenEntity(
                token = refreshToken,
                expiresAt = refreshTokenExpiration,
                user = user
            )
        )

        return accessToken to refreshToken
    }

    override fun getRefreshTokenExpiration(token: String): String {
        TODO("Not yet implemented")
    }

    private fun createToken(user: UserEntity, expiration: Date, claims: Map<String, Any> = mapOf()): String =
        Jwts.builder()
            .claims(claims)
            .subject(user.login)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expiration)
            .signWith(secretKey)
            .compact()

    private fun getAccessTokenExpirationDate() =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpirationDate() =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}