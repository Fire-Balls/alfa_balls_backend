package org.fireballs.alfaballs.app.service;

import org.fireballs.alfaballs.app.repository.UserRepository;
import org.fireballs.alfaballs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");
        user.setAvatar("avatar.png".getBytes());
    }

    @Test
    void saveUser_shouldPersistUser() {
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.saveUser(user);

        assertEquals(1L, saved.getId());
        verify(userRepository).save(user);
    }

    @Test
    void saveUser_shouldThrowIfUserIsNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(null));
    }

    @Test
    void updateUser_shouldUpdateUserFields() {
        User updated = new User();
        updated.setName("Jane Doe");
        updated.setEmail("jane@example.com");
        updated.setAvatar("jane.png".getBytes());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        User result = userService.updateUser(1L, updated);

        assertEquals("Jane Doe", result.getName());
        assertEquals("jane@example.com", result.getEmail());
        assertArrayEquals("jane.png".getBytes(), result.getAvatar());
        verify(userRepository).save(user);
    }

    @Test
    void updateUser_shouldThrowIfNewUserNull() {
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(1L, null));
    }

    @Test
    void getUserById_shouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_shouldThrowIfNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
    }

    @Test
    void deleteUser_shouldCallRepositoryDelete() {
        userService.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }
}
