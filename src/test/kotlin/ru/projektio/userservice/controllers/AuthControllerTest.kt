package ru.projektio.userservice.controllers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import ru.projektio.userservice.database.repository.UserDao
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse
import ru.projektio.userservice.service.AuthService


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthControllerTest {

  @Autowired
  lateinit var userDao: UserDao

  @LocalServerPort
  private var localServerPort: Int = 0

  @Autowired
  lateinit var testRestTemplate: TestRestTemplate

  private lateinit var host: String

@BeforeAll
 fun setup() {
  host = "http://localhost:$localServerPort"
  userDao.deleteAll()
 }

 @Test
 fun `register user should return 201 and tokens`() {
  val request = RegisterRequest(
   "misha_zxc",
   "Test@test.ru",
   "mega_parol"
  )

  val response = testRestTemplate.postForEntity(
   "$host/auth/register",
   request,
   AuthResponse::class.java
  )
  assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
  assertThat(response.body?.accessToken).isNotBlank()
  assertThat(response.body?.refreshToken).isNotBlank()
 }

 @Test
 fun `login with valid credentials should return 200 and tokens`() {
  val request = LoginRequest(
   login = "misha_zxc",
   email = null,
   password = "mega_parol"
  )

  val response = testRestTemplate.postForEntity(
   "auth/login",
   request,
   AuthResponse::class.java
  )

  assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
  assertThat(response.body?.accessToken).isNotBlank()
  assertThat(response.body?.refreshToken).isNotBlank()
 }





}