package ru.projektio.userservice.database.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import ru.projektio.userservice.database.entity.UserEntity
import kotlin.test.*

@DataJpaTest
class UserDaoTest {

    @Autowired lateinit var userDao: UserDao

    @Test
    fun `save and find user`() {
        val user = UserEntity(login = "jpa_user", email = "jpa@test.com", passwordHash = "hash")
        userDao.save(user)

        val found = userDao.findByLogin("jpa_user")
        assertNotNull(found)
        assertEquals("jpa@test.com", found.email)
    }
}