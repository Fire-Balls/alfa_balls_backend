package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import jakarta.validation.Valid;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProjectDetailsDto extends RepresentationModel<ProjectDetailsDto> {

        @NotNull
        private Long id;

        @NotNull
        @Size(min = 1, max = 255)
        private String name;

        @NotNull
        private List<@Valid BoardDto> kanbanBoards;

        @NotNull
        private List<@Valid IssueShortcutDto> issues;
}


