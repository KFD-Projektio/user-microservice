package ru.projektio.userservice.service.impl

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.projektio.userservice.database.repository.UserDao
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.exception.exceptions.BadAuthRequestException
import ru.projektio.userservice.service.AuthService
import ru.projektio.userservice.service.UserService

@Service
class AuthServiceImpl(
    private val userDao: UserDao,
    private val passwordEncoder: PasswordEncoder,
    private val userService: UserService
) : AuthService {
    override fun authenticate(request: LoginRequest): ResponseEntity<AuthResponse> {
        val login: String = request.login ?: throw BadAuthRequestException("No login were provided in request.")
        val user = userDao.findByLogin(login) ?: throw BadAuthRequestException("No login exists.")

        TODO("Finish login (need to create registration) ")

    }

    override fun register(request: RegisterRequest): ResponseEntity<AuthResponse> {
        val user = userService.create(request)
        TODO(" Ustal ")
    }

    override fun refresh(request: RefreshRequest): ResponseEntity<AuthResponse> {
        TODO("Not yet implemented")
    }

}