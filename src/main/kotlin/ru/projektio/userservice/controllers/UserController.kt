package ru.projektio.userservice.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.projektio.userservice.database.repository.UserDao
import ru.projektio.userservice.dto.request.RegisterRequest
import ru.projektio.userservice.dto.response.UserResponse
import ru.projektio.userservice.service.UserService

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userDao: UserDao,
) {
    @GetMapping
    fun getUsers(): ResponseEntity<List<UserResponse>> =
        ResponseEntity.ok( userDao.findAll().map { UserResponse.fromEntity(it) })

    @PostMapping
    fun createUser(@RequestBody request: RegisterRequest): ResponseEntity<UserResponse> =
        ResponseEntity.ok(userService.create(request))
}