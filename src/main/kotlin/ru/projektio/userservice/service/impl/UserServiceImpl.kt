package ru.projektio.userservice.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.database.repository.UserDao
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.exception.exceptions.user.DuplicateEmailException
import ru.projektio.userservice.exception.exceptions.user.DuplicateLoginException
import ru.projektio.userservice.service.UserService


@Service
class UserServiceImpl(
    private val userDao: UserDao,
    private val passwordEncoder: PasswordEncoder
) : UserService {

    private val passwordRegex: Regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
    private val emailRegex: Regex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()

    private fun validateRequest(request: RegisterRequest) {
        require(request.login.isNotBlank()) { "Login cannot be empty" }
        require(request.password.matches(passwordRegex)) {
            "Password must contain: 8+ chars, 1 digit, 1 lowercase and uppercase letter"
        }
        require(request.email.matches(emailRegex)) { "Invalid email format" }
    }

    private fun checkUniqueness(login: String, email: String) {
        if (userDao.existsByLogin(login)) {
            throw DuplicateLoginException("Login $login exists")
        }
        if (userDao.existsByEmail(email)) {
            throw DuplicateEmailException("Email $email registered")
        }
    }


    @Transactional
    override fun create(request: RegisterRequest): UserEntity {

        validateRequest(request)
        checkUniqueness(request.login, request.email)

        val user = UserEntity(
            login = request.login,
            email = request.email,
            passwordHash = passwordEncoder.encode(request.password),
        )
        return userDao.save(user)
    }
}