package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EpicShortcutDto extends RepresentationModel<EpicShortcutDto> {

        @NotNull
        @Positive
        private Long projectId;

        @NotBlank
        private String epicName;

        @NotNull
        @Positive
        private Long epicId;
}


