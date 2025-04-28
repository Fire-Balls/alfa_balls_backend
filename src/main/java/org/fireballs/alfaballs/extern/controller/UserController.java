package org.fireballs.alfaballs.extern.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.assembler.UserDetailsAssembler;
import org.fireballs.alfaballs.extern.assembler.UserShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.MessageDto;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<UserDto.Details> createUser(@Validated(PostPutGroup.class) @RequestBody UserDto.Shortcut userDto) {
        User savedUser = userService.saveUser(userShortcutAssembler.toEntity(userDto));
        return new ResponseEntity<>(userDetailsAssembler.toModel(savedUser), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto.Details> getUser(@PathVariable Long userId) {
        User retrievedUser = userService.getUserById(userId);
        return new ResponseEntity<>(userDetailsAssembler.toModel(retrievedUser), HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto.Details> updateUser(@PathVariable Long userId,
                                              @Validated(PostPutGroup.class) @RequestBody UserDto.Shortcut userDto) {
        User updatedUser = userService.updateUser(userId, userShortcutAssembler.toEntity(userDto));
        return new ResponseEntity<>(userDetailsAssembler.toModel(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        MessageDto response = new MessageDto();
        response.setMessage("User with ID " + userId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
