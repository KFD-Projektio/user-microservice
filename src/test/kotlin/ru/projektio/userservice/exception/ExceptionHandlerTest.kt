package ru.projektio.userservice.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import ru.projektio.userservice.dto.request.RegisterRequest

@SpringBootTest
@AutoConfigureMockMvc
class ExceptionHandlerTest {

    @Autowired lateinit var mockMvc: MockMvc

    @Test
    fun `duplicate registration returns 409`() {
        val request = RegisterRequest("duplicate", "duplicate@test.com", "Pass123!")
        val json = jacksonObjectMapper().writeValueAsString(request)

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))

        mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andExpect(status().isConflict)
            .andExpect(jsonPath("$.message").value("Login duplicate exists"))
    }
}