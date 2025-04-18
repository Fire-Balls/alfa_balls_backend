package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProjectRequestDto(
        @NotBlank(message = "Project name is required")
        String projectName,

        @NotBlank(message = "Project code is required")
        String projectCode
) {}