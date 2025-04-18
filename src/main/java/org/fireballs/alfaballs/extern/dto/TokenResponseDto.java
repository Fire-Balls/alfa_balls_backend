package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;

public record TokenResponseDto(
        @NotBlank(message = "Access token is required")
        String accessToken,

        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {}
