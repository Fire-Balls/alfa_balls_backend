package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageDto(
        @NotBlank(message = "Message cannot be blank")
        String message
) {}
