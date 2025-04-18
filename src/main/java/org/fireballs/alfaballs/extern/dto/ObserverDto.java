package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ObserverDto(
        @NotNull
        @Size(min = 1, max = 100)
        String role,

        @NotNull
        @Valid ProfileDto profile
) {}

