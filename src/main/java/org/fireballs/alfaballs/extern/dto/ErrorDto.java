package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ErrorDto(
        @Positive(message = "Status must be positive")
        int status,

        @NotBlank(message = "Error message must not be blank")
        String message
) {}
