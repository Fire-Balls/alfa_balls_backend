package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFieldDefinitionRequestDto(
        @NotNull
        @Size(min = 1, max = 100)
        String name,

        @NotNull
        String dataType
) {
}

