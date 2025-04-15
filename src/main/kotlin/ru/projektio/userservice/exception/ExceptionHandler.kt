package ru.projektio.userservice.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.dto.response.ErrorResponse
import ru.projektio.userservice.exception.exceptions.BadAuthRequestException

@ControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(BadAuthRequestException::class)
    fun handleBadAuthRequest(e: BadAuthRequestException): ResponseEntity<ErrorResponse> {
        return ResponseEntity
            .badRequest()
            .body(ErrorResponse(e.message!!))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity
            .badRequest()
            .body(ErrorResponse(e.message!!))
}