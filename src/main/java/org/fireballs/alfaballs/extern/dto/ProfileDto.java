package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProfileDto(
        @NotNull String id,

        @NotNull
        @Size(min = 1, max = 255)
        String fullName,

        @NotNull
        @Email
        String email,

        @NotNull
        String avatar
) {}

