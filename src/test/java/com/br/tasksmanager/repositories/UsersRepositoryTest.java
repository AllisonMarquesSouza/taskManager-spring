package com.br.tasksmanager.repositories;

import com.br.tasksmanager.models.Users;
import com.br.tasksmanager.util.UsersCreate;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Log4j2
class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    void tearDown() {
        usersRepository.deleteAll();
    }

    @Test
    void findByUsername_ExistingUser_ReturnsUser() {
        Users user1 = usersRepository.save(UsersCreate.createUser());

        Users allison = (Users) usersRepository.findByUsername(user1.getUsername());

        assertNotNull(allison);
        assertEquals(user1, allison);
    }

    @Test
    void existsByUsername_ExistingUserWithSuchUsername_ReturnsTrue() {
        Users user1 = usersRepository.save(UsersCreate.createUser());

        boolean result = usersRepository.existsByUsername(user1.getUsername());

        assertTrue(result);
    }

    @Test
    void existsByEmail_ExistingUserWithSuchEmail_ReturnsTrue() {
        Users user1 = usersRepository.save(UsersCreate.createUser());

        boolean result = usersRepository.existsByEmail(user1.getEmail());

        assertTrue(result);
    }
}