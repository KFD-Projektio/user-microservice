package ru.projektio.userservice.controllers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import ru.projektio.userservice.dto.request.LoginRequest
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.AuthResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

 @Autowired lateinit var restTemplate: TestRestTemplate

 @Test
 fun `register user returns 201`() {
  val request = RegisterRequest("user2", "user2@test.com", "Pass123!")
  val response = restTemplate.postForEntity("/auth/register", request, AuthResponse::class.java)

  assertEquals(HttpStatus.CREATED, response.statusCode)
  assertNotNull(response.body?.accessToken)
 }

 @Test
 fun `login with invalid credentials returns 400`() {
  val request = LoginRequest("unknown", null, "wrong")
  val response = restTemplate.postForEntity("/auth/login", request, String::class.java)

  assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
 }

 @Test
 fun `register user should return 201 and tokens`() {
  val request = RegisterRequest(
   "misha_zxc",
   "Test@test.ru",
   "Mega_parol!22"
  )

  val response = restTemplate.postForEntity(
   "/auth/register",
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
   password = "Mega_parol!22"
  )

  val response = restTemplate.postForEntity(
   "/auth/login",
   request,
   AuthResponse::class.java
  )

  assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
  assertThat(response.body?.accessToken).isNotBlank()
  assertThat(response.body?.refreshToken).isNotBlank()
 }
}