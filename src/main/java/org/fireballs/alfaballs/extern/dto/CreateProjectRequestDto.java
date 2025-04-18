package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.*;

public record CreateProjectRequestDto(
        @NotBlank(message = "Project name is required")
        String projectName,

        @NotBlank(message = "Project code is required")
        String projectCode
) {}