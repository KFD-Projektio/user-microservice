package ru.projektio.userservice.service

import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.dto.request.RegisterRequest

interface UserService {
    fun create(request: RegisterRequest): UserEntity
    fun getAuthorizedUser(): UserEntity
}
