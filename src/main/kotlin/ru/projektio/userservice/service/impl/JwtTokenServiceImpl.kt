package ru.projektio.userservice.service.impl

import ru.projektio.userservice.service.JwtTokenService

class JwtTokenServiceImpl : JwtTokenService {
    override fun validateToken(token: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun createRefreshToken(token: String): String {
        TODO("Not yet implemented")
    }

    override fun createAccessToken(token: String): String {
        TODO("Not yet implemented")
    }

    override fun getRefreshTokenExpiration(token: String): String {
        TODO("Not yet implemented")
    }

}