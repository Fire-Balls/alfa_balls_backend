package org.fireballs.alfaballs.extern.dto.newdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.dto.IssueShortcutDto;
import org.fireballs.alfaballs.extern.dto.group.DetailsGroup;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDto extends RepresentationModel<ProjectDto> {
    @NotNull(message = "Project ID is required", groups = {ShortcutGroup.class, DetailsGroup.class})
    @Positive(message = "Project ID must be positive")
    Long projectId;

    @NotBlank(message = "Project name is required", groups = {DetailsGroup.class, ShortcutGroup.class, PostPutGroup.class})
    @Size(max = 100, message = "Project name must be less than 100 characters")
    String projectName;

    @NotBlank(message = "Project code is required", groups = {DetailsGroup.class, ShortcutGroup.class, PostPutGroup.class})
    @Size(min = 2, max = 10, message = "Project code must be between 2 and 10 characters")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Project code must contain only uppercase letters, numbers and underscores")
    String projectCode;

    @NotNull(groups = DetailsGroup.class)
    private List<@Valid BoardDto> kanbanBoards;

    @NotNull(groups = DetailsGroup.class)
    private List<@Valid IssueDto> issues;

    public Project toEntity() {
        return Project.builder()
                .id(projectId)
                .name(projectName)
                .code(projectCode)
                .boards(kanbanBoards == null ? new ArrayList<>() : kanbanBoards.stream()
                        .map(BoardDto::toEntity)
                        .toList())
                .build();
    }
}
