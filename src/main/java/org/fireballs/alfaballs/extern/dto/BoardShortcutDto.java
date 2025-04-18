package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record BoardShortcutDto(
        @NotNull
        @Valid
        ProjectShortcutDto project,
        @NotNull
        @PositiveOrZero(message = "Issues count cannot be negative")
        Integer issuesCount,
        @NotNull Integer boardId,
        @NotNull String boardName
) {}
