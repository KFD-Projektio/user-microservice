package ru.projektio.userservice.database.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(
    @Column(name = "token", length = 512)
    val token: String,

    @Column(name = "expires_at")
    val expiresAt: Date,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity
) : AbstractEntity()