package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record BoardDto(
        @NotNull
        @Valid
        ProjectShortcutDto project,

        @NotNull
        List<@NotNull @Valid IssueShortcutDto> issues,

        @NotNull
        @Valid
        BoardShortcutDto shortcut
) {
}
