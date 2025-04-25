package ru.projektio.userservice.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class UserEntity(
    @Column(name = "login")
    var login: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password", length = 265)
    val passwordHash: String,
) : AbstractEntity()
