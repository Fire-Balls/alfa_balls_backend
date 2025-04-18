package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ProjectDetailsDto(
        @NotNull Long id,

        @NotNull
        @Size(min = 1, max = 255)
        String name,

        @NotNull
        List<@Valid BoardDto> kanbanBoards,

        @NotNull
        List<@Valid IssueShortcutDto> issues
) {}

