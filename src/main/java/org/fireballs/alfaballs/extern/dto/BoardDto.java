package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import jakarta.validation.Valid;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class BoardDto extends RepresentationModel<BoardDto> {

        @NotNull
        @Valid
        private ProjectShortcutDto project;

        @NotNull
        private List<@NotNull @Valid IssueShortcutDto> issues;

        @NotNull
        @Valid
        private BoardShortcutDto shortcut;
}


