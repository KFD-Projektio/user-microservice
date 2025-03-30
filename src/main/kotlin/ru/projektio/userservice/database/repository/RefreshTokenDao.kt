package ru.projektio.userservice.database.repository

import org.springframework.data.repository.CrudRepository
import ru.projektio.userservice.database.entity.RefreshTokenEntity

interface RefreshTokenDao : CrudRepository<RefreshTokenEntity, Long> {
    fun deleteByUserId(userId: Long)
    fun findByToken(token: String): RefreshTokenEntity?
}