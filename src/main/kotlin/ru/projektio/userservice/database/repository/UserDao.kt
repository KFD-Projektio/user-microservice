package ru.projektio.userservice.database.repository

import org.springframework.data.repository.CrudRepository
import ru.projektio.userservice.database.entity.UserEntity

interface UserDao : CrudRepository<UserEntity, Long>