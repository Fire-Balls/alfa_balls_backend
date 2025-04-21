package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardShortcutDto extends RepresentationModel<BoardShortcutDto> {

        @NotNull
        @Valid
        private ProjectShortcutDto project;

        @NotNull
        @PositiveOrZero(message = "Issues count cannot be negative")
        private Integer issuesCount;

        @NotNull
        private Integer boardId;

        @NotNull
        private String boardName;
}
