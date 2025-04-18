package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record IssueDto(
        @NotNull String id,

        @NotNull
        @Size(min = 1, max = 255)
        String title,

        @NotNull
        List<@Valid FieldDto> fields,

        @NotNull
        List<@Valid IssueShortcutDto> depends,

        @NotNull
        List<@Valid ObserverDto> observers,

        @NotNull
        @Valid EpicShortcutDto epic
) {}

