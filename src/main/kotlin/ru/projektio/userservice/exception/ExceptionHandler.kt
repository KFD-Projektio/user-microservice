package ru.projektio.userservice.exception

import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.dto.response.ErrorResponse
import ru.projektio.userservice.exception.exceptions.BadAuthRequestException
import ru.projektio.userservice.exception.exceptions.CredentialsMismatchException
import ru.projektio.userservice.exception.exceptions.user.DuplicateEmailException
import ru.projektio.userservice.exception.exceptions.user.DuplicateLoginException

@ControllerAdvice
class   ExceptionHandler {

    @ExceptionHandler(BadAuthRequestException::class)
    fun handleBadAuthRequest(e: BadAuthRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message!!))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message!!))

    @ExceptionHandler(CredentialsMismatchException::class)
    fun handleCredentialsMismatch(e: CredentialsMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ErrorResponse(e.message ?: "Invalid credentials"))
    }

    // Конфликт email
    @ExceptionHandler(DuplicateEmailException::class)
    fun handleDuplicateEmail(e: DuplicateEmailException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(e.message ?: "Email already registered"))
    }

    // Конфликт логина
    @ExceptionHandler(DuplicateLoginException::class)
    fun handleDuplicateLogin(e: DuplicateLoginException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(ErrorResponse(e.message ?: "Login already taken"))
    }

    // Общий обработчик для непредвиденных ошибок
    @ExceptionHandler(Exception::class)
    fun handleGenericException(e: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse("Internal server error ${e.message}"))
    }
}