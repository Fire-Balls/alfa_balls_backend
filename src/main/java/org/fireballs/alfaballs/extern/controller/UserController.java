package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.assembler.details.UserDetailsAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.UserShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.MessageDto;
import org.fireballs.alfaballs.extern.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailsAssembler userDetailsAssembler;
    private final UserShortcutAssembler userShortcutAssembler;

    // Moved to AuthController
//    @PostMapping
//    public ResponseEntity<UserDto.Details> createUser(@Validated(PostPutGroup.class) @RequestBody UserDto.Shortcut userDto) {
//        User savedUser = userService.saveUser(userShortcutAssembler.toEntity(userDto));
//        return new ResponseEntity<>(userDetailsAssembler.toModel(savedUser), HttpStatus.CREATED);
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.Details> getUser(@PathVariable Long userId) {
        User retrievedUser = userService.getUserById(userId);
        return ResponseEntity.ok(userDetailsAssembler.toModel(retrievedUser));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.Details> updateUser(@PathVariable Long userId,
                                              @Validated(PostPutGroup.class) @RequestBody UserDto.Shortcut userDto) {
        User entity = userShortcutAssembler.toEntity(userDto);
        User updatedUser = userService.updateUser(userId, entity);
        return ResponseEntity.ok(userDetailsAssembler.toModel(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(new MessageDto("User with ID " + userId + " has been deleted"));

    }
}
