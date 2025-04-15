package ru.projektio.userservice.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import ru.projektio.userservice.database.entity.UserEntity
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val login: String,
    val email: String,
    @field:JsonProperty("board_ids") val boardIds: List<Long>,
    @field:JsonProperty("created_at") val createdAt: LocalDateTime,
) {
    companion object {
        fun fromEntity(entity: UserEntity): UserResponse {
            return UserResponse(
                id = entity.id,
                login = entity.login,
                email = entity.email,
                createdAt = entity.createdAt,
                boardIds = entity.boardIDs
            )
        }
    }
}
