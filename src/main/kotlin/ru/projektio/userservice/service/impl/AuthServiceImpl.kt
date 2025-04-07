package ru.projektio.userservice.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.projektio.userservice.database.entity.RefreshTokenEntity
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.database.repository.RefreshTokenDao
import ru.projektio.userservice.database.repository.UserDao
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.exception.exceptions.BadAuthRequestException
import ru.projektio.userservice.exception.exceptions.CredentialsMismatchException
import ru.projektio.userservice.service.AuthService
import ru.projektio.userservice.service.JwtTokenService
import ru.projektio.userservice.service.UserService

@Service
class AuthServiceImpl(
    private val userDao: UserDao,
    private val userService: UserService,
    private val refreshTokenDao: RefreshTokenDao,
    private val jwtTokenService: JwtTokenService,
    private val passwordEncoder: PasswordEncoder
) : AuthService {
    @Transactional
    override fun authenticate(request: LoginRequest): AuthResponse {
        val user = getUser(request) ?: throw BadAuthRequestException("No user exists.")

        if (!passwordEncoder.matches(request.password, user.passwordHash))
            throw CredentialsMismatchException("Password mismatch")

        return authUser(user)
    }
    @Transactional
    override fun register(request: RegisterRequest): AuthResponse {
        val user = userService.create(request)
        return authUser(user)
    }

    @Transactional
    override fun refresh(request: RefreshRequest): AuthResponse {
        val refreshToken: RefreshTokenEntity = refreshTokenDao.findByToken(request.refreshToken)
            ?: throw BadAuthRequestException("No provided refresh token exists.")

        if (jwtTokenService.getTokenExpiration(refreshToken.token).time > System.currentTimeMillis()) {
            refreshTokenDao.delete(refreshToken)
            throw BadAuthRequestException("Token expired.")
        }

        val user: UserEntity = refreshToken.user

        return authUser(user)
    }

    private fun getUser(request: LoginRequest): UserEntity? = when {
            request.login != null -> userDao.findByLogin(request.login)
            request.email != null -> userDao.findByEmail(request.email)
            else -> throw BadAuthRequestException(("No login and email were provided in request."))
        }

    private fun authUser(user: UserEntity): AuthResponse {
        refreshTokenDao.deleteByUserId(user.id)
        
        val (accessToken, refreshToken) = jwtTokenService.createTokenPair(user)

        refreshTokenDao.save(
            RefreshTokenEntity(
                refreshToken,
                jwtTokenService.getTokenExpiration(refreshToken),
                user
            )
        )

        return AuthResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id,
            email = user.email
        )
    }
}