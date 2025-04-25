package org.fireballs.alfaballs.extern.controller;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.assembler.UserAssembler;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
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
    private final UserAssembler userAssembler;

    @JsonView(DetailsView.class)
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Validated(PostPutGroup.class) @RequestBody UserDto userDto) {
        User savedUser = userService.saveUser(userAssembler.toEntity(userDto));
        return new ResponseEntity<>(userAssembler.toModel(savedUser), HttpStatus.CREATED);
    }

    @JsonView(DetailsView.class)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User retrievedUser = userService.getUserById(userId);
        return new ResponseEntity<>(userAssembler.toModel(retrievedUser), HttpStatus.OK);
    }

    @JsonView(DetailsView.class)
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId,
                                              @Validated(PostPutGroup.class) @RequestBody UserDto userDto) {
        User existingUser = userService.getUserById(userId);
        existingUser.setName(userDto.getFullName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setAvatar(userDto.getAvatar().getBytes());

        User updatedUser = userService.saveUser(existingUser);
        return new ResponseEntity<>(userAssembler.toModel(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);

        MessageDto response = new MessageDto();
        response.setMessage("User with ID " + userId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
