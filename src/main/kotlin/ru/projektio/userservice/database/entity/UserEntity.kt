package ru.projektio.userservice.database.entity

import jakarta.persistence.*

@Entity
@Table(name = "`user`")
class UserEntity(
    @Column(name = "login")
    var login: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    val passwordHash: String,
) : AbstractEntity() {

    @ElementCollection
    @CollectionTable(name = "user_boards", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "board_id")
    val boardIDs: MutableList<Long> = mutableListOf()
}
