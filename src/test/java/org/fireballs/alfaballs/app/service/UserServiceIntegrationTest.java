package org.fireballs.alfaballs.app.service;

import org.fireballs.alfaballs.app.repository.UserRepository;
import org.fireballs.alfaballs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setAvatar("avatar.png".getBytes());
    }

    @Test
    void saveUser_shouldPersistUserInDatabase() {
        User saved = userService.saveUser(user);

        assertNotNull(saved.getId());

        Optional<User> fromDb = userRepository.findById(saved.getId());
        assertTrue(fromDb.isPresent());
        assertEquals("test@example.com", fromDb.get().getEmail());
    }

    @Test
    void getUserById_shouldReturnPersistedUser() {
        User saved = userService.saveUser(user);

        User found = userService.getUserById(saved.getId());

        assertEquals(saved.getId(), found.getId());
        assertEquals("Test User", found.getName());
    }

    @Test
    void updateUser_shouldUpdateFields() {
        User saved = userService.saveUser(user);

        User updated = new User();
        updated.setName("Updated Name");
        updated.setEmail("updated@example.com");
        updated.setAvatar("updated.png".getBytes());

        User result = userService.updateUser(saved.getId(), updated);

        assertEquals("Updated Name", result.getName());
        assertEquals("updated@example.com", result.getEmail());
        assertArrayEquals("updated.png".getBytes(), result.getAvatar());
    }

    @Test
    void deleteUser_shouldRemoveUserFromDatabase() {
        User saved = userService.saveUser(user);

        userService.deleteUser(saved.getId());

        assertFalse(userRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void getUserById_shouldThrowIfUserNotFound() {
        long nonexistentId = 9999L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(nonexistentId);
        });

        assertTrue(exception.getMessage().contains("User with id " + nonexistentId + " not found"));
    }
}

