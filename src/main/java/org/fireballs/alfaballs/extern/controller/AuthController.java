package org.fireballs.alfaballs.extern.controller;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.repository.RefreshTokenRepository;
import org.fireballs.alfaballs.app.security.JwtService;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.RefreshToken;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.assembler.details.UserDetailsAssembler;
import org.fireballs.alfaballs.extern.dto.newdtos.security.LoginRequestDto;
import org.fireballs.alfaballs.extern.dto.newdtos.security.RefreshTokenDto;
import org.fireballs.alfaballs.extern.dto.newdtos.security.RegistrationRequestDto;
import org.fireballs.alfaballs.extern.dto.newdtos.security.TokenResponseDto;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserDetailsAssembler userDetailsAssembler;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration-ms}")
    private long refreshTokenMs;

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
            String token = jwtService.generateAccessToken(authRequest.getEmail());

            User user = userService.getUserByEmail(authRequest.getEmail());

            long expiresTtlMs = System.currentTimeMillis() + refreshTokenMs;
            RefreshToken refreshToken = RefreshToken.builder()
                    .user(user)
                    .refreshToken(jwtService.generateRefreshToken(authRequest.getEmail(), new Date(expiresTtlMs)))
                    .expiresAt(Instant.ofEpochMilli(expiresTtlMs))
                    .build();

            refreshTokenRepository.save(refreshToken);

            return ResponseEntity.ok(new TokenResponseDto(token, refreshToken.getRefreshToken(), user.getId()));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<TokenResponseDto> refreshToken(@Valid @RequestBody RefreshTokenDto refreshToken) {
        var refreshTokenEntity = refreshTokenRepository
                .findByRefreshTokenAndExpiresAtAfter(refreshToken.getRefreshToken(), Instant.now())
                .orElseThrow(ValidationException::new);

        var newAccessToken = jwtService.generateAccessToken(refreshTokenEntity.getUser().getEmail());
        return ResponseEntity.ok(new TokenResponseDto(newAccessToken, refreshTokenEntity.getRefreshToken(), refreshTokenEntity.getUser().getId()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> revokeToken(@RequestBody String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
        return ResponseEntity.noContent().build();
    }
}
