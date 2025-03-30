package ru.projektio.userservice.database.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(
    @Column(name = "token")
    val token: String,

    @Column(name = "expires_at")
    val expiresAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: UserEntity
) : AbstractEntity()