package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.*;

public record IssueShortcutDto(
        @NotNull(message = "ID is required")
        @Positive(message = "ID must be positive")
        Long id,

        @NotNull(message = "Project ID is required")
        @Positive(message = "Project ID must be positive")
        Long projectId,

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be less than 200 characters")
        String title,

        @NotBlank(message = "Type is required")
        @Pattern(regexp = "^(BUG|TASK|FEATURE)$",
                message = "Type must be BUG, TASK or FEATURE")
        String type,

        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$",
                message = "Invalid status value")
        String status,

        @NotBlank(message = "Assignee is required")
        @Email(message = "Assignee must be a valid email")
        String assignee
) {}
