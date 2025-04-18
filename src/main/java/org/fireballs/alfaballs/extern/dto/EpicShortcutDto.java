package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EpicShortcutDto(
        @NotNull
        @Positive
        Long projectId,

        @NotBlank
        String epicName,

        @NotNull
        @Positive
        Long epicId
) {}

