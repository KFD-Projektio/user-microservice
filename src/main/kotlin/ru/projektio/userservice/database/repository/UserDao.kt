package ru.projektio.userservice.database.repository

import org.springframework.data.repository.CrudRepository
import ru.projektio.userservice.database.entity.UserEntity

interface UserDao : CrudRepository<UserEntity, Long> {
    fun findByLogin(login: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
    fun existsByLogin(login: String): Boolean
}