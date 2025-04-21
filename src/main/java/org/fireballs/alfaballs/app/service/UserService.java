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
    private final UserRepository UserRepository;

    public User saveUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null");
        }

        UserRepository.save(user);
        log.info("User {} was created", user.getId());

        return user;
    }

    public User getUserById(long userId) {
        var searchedGame = UserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));
        log.info("User {} was found", userId);
        return searchedGame;
    }

    public void deleteUser(long userId) {
        UserRepository.deleteById(userId);
        log.info("User {} was deleted", userId);
    }

    public List<User> getAllUsers() {
        log.info("Get all Users");
        return UserRepository.findAll();
    }
}
