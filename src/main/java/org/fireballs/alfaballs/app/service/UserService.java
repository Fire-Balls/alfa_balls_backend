package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.UserRepository;
import org.fireballs.alfaballs.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }

        User savedUser = userRepository.save(user);
        log.info("User {} was created", user.getId());

        return savedUser;
    }

    public User getUserById(long userId) {
        var searchedGame = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        log.info("User {} was found", userId);
        return searchedGame;
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
        log.info("User {} was deleted", userId);
    }

    public List<User> getAllUsers() {
        log.info("Get all Users");
        return userRepository.findAll();
    }
}
