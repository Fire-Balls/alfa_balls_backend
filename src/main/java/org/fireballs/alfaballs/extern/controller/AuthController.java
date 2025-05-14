package org.fireballs.alfaballs.extern.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.security.JwtService;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.assembler.details.UserDetailsAssembler;
import org.fireballs.alfaballs.extern.dto.newdtos.security.LoginRequestDto;
import org.fireballs.alfaballs.extern.dto.newdtos.security.RegistrationRequestDto;
import org.fireballs.alfaballs.extern.dto.newdtos.security.TokenResponseDto;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserDetailsAssembler userDetailsAssembler;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDto.Details> register(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        User newUser = User.builder()
                .email(registrationRequestDto.getEmail())
                .password(registrationRequestDto.getPassword())
                .globalRole(User.Role.valueOf(registrationRequestDto.getGlobalRole()))
                .build();
        User savedUser = userService.saveUser(newUser);
        return ResponseEntity.ok(userDetailsAssembler.toModel(savedUser));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenResponseDto> authenticateAndGetToken(@Valid @RequestBody LoginRequestDto authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
