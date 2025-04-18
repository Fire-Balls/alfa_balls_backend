package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.*;

public record ProjectShortcutDto(
        @NotNull(message = "Project ID is required")
        @Positive(message = "Project ID must be positive")
        Long projectId,

        @NotBlank(message = "Project name is required")
        @Size(max = 100, message = "Project name must be less than 100 characters")
        String projectName,

        @NotBlank(message = "Project code is required")
        @Size(min = 2, max = 10, message = "Project code must be between 2 and 10 characters")
        @Pattern(regexp = "^[A-Z0-9_]+$", message = "Project code must contain only uppercase letters, numbers and underscores")
        String projectCode
) {}
