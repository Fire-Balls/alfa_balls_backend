package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FieldDto(
        @NotNull
        @Size(min = 1, max = 100)
        String fieldName,

        @NotNull
        @Size(min = 1, max = 1000)
        String fieldValue,

        @NotNull
        Boolean required,

        String type // допустим nullable
) {}

