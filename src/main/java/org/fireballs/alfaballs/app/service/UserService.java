package org.fireballs.alfaballs.app.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.UserRepository;
import org.fireballs.alfaballs.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //todo мб можно как-то лучше создавать первого суперпользователя
    @PostConstruct
    private void createFirstSuperUser() {
        User superUser = User.builder()
                .email("super123@urfu.ru")
                .password("super")
                .globalRole(User.Role.ADMIN)
                .build();

        saveUser(superUser);
    }

    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("User {} was saved", user.getId());

        return savedUser;
    }

    public User updateUser(long existingUserId, User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("New user is null");
        }

        User existingUser = getUserById(existingUserId);

        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setAvatar(newUser.getAvatar());

        return saveUser(existingUser);
    }

    public User getUserById(long userId) {
        var searchedGame = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        log.info("User {} was found by id", userId);
        return searchedGame;
    }

    public User getUserByEmail(String email) {
        var searchedGame = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with email " + email + " not found"));
        log.info("User {} was found by email", email);
        return searchedGame;
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User {} was deleted", userId);
    }
}
