package ru.projektio.userservice.dto.mappers

import ru.projektio.userservice.database.entity.UserEntity
import ru.projektio.userservice.dto.response.UserResponse

class UserMapper : AbstractMapper<UserEntity, UserResponse> {
    override fun mapEntityToResponse(entity: UserEntity): UserResponse {
        return UserResponse(
            id = entity.id,
            login = entity.login,
            email = entity.email,
            createdAt = entity.createdAt
        )
    }
}