package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBoardRequestDto(
        @NotBlank
        @Size(max = 255)
        String boardName
) {}

