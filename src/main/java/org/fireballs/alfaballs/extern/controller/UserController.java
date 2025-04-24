package org.fireballs.alfaballs.extern.controller;

import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Validated(PostPutGroup.class) @RequestBody UserDto userDto) {
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Validated(PostPutGroup.class) @PathVariable Long userId, @RequestBody UserDto userDto) {
    }

    //todo поменять на MessageDto
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long userId) {
    }
}
