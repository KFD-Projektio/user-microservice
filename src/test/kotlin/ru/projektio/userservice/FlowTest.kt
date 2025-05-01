package ru.projektio.userservice

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RefreshRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlowTest {

    @Autowired lateinit var restTemplate: TestRestTemplate

    @Test
    fun `full auth flow`() {
        val registerRequest = RegisterRequest("mr_user", "full@test.com", "Pass123!")
        val registerResponse = restTemplate.postForEntity("/auth/register", registerRequest, AuthResponse::class.java)

        val loginRequest = LoginRequest("mr_user", null, "Pass123!")
        val loginResponse = restTemplate.postForEntity("/auth/login", loginRequest, AuthResponse::class.java)

        val refreshRequest = RefreshRequest(loginResponse.body!!.refreshToken)
        val refreshResponse = restTemplate.postForEntity("/auth/refresh", refreshRequest, Map::class.java)

        val logoutResponse = restTemplate.postForEntity("/auth/logout", refreshRequest, Map::class.java)

        assertEquals(HttpStatus.CREATED, registerResponse.statusCode)
        assertEquals(HttpStatus.OK, loginResponse.statusCode)
        assertEquals(HttpStatus.OK, refreshResponse.statusCode)
        assertEquals(HttpStatus.OK, logoutResponse.statusCode)
    }
}