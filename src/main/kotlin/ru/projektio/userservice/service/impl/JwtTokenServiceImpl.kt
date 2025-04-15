package ru.projektio.userservice.service.impl

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import ru.projektio.userservice.config.properties.JwtProperties
import ru.projektio.userservice.database.entity.RefreshTokenEntity
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.database.repository.RefreshTokenDao
import ru.projektio.userservice.service.JwtTokenService
import java.util.*

@Service
class JwtTokenServiceImpl(
    private val jwtProperties: JwtProperties,
    private val refreshTokenDao: RefreshTokenDao
) : JwtTokenService {

    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    override fun validateToken(token: String): Boolean {
        return try {
            getAllClaimsFromToken(token)
            isTokenNotExpired(token)
        } catch (ex: MalformedJwtException) {
            false
        } catch (ex: ExpiredJwtException) {
            false
        } catch (ex: IllegalArgumentException) {
            false
        }
    }

    override fun getUsernameFromToken(token: String): String {
        return getAllClaimsFromToken(token).subject
    }

    override fun createTokenPair(user: UserEntity): Pair<String, String> {
        val accessToken = createToken(user, getAccessTokenExpirationDate())

        val refreshTokenExpiration = getRefreshTokenExpirationDate()
        val refreshToken = createToken(user, refreshTokenExpiration)

        refreshTokenDao.save(
            RefreshTokenEntity(
                token = refreshToken,
                user = user,
                expiresAt = getTokenExpiration(refreshToken)
            )
        )
        return accessToken to refreshToken
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

    override fun getTokenExpiration(token: String): Date =
        getAllClaimsFromToken(token).expiration

    override fun isTokenNotExpired(token: String): Boolean =
        getAllClaimsFromToken(token).expiration.after(Date())

    private fun getAllClaimsFromToken(token: String): Claims =
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload
}