package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record UserProfileDto(
        @NotNull Integer id,

        @NotNull
        @Size(min = 1, max = 255)
        String fullName,

        @NotNull
        @Email
        String email,

        @NotNull
        String avatar,

        @NotNull
        @NotEmpty
        List<@NotBlank @Size(max = 100) String> roles
) {}

