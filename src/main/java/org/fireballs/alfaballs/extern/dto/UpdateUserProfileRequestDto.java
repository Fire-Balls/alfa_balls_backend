package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserProfileRequestDto(
        @NotBlank
        @Size(max = 255)
        String fullName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String avatar,

        @NotEmpty
        List<@NotBlank @Size(max = 100) String> roles
) {}
