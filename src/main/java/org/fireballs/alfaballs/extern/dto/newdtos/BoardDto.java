package org.fireballs.alfaballs.extern.dto.newdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.dto.group.DetailsGroup;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class BoardDto extends RepresentationModel<BoardDto> {
    @NotNull(groups = {ShortcutGroup.class, DetailsGroup.class})
    @Valid
    private ProjectDto project;

    @NotNull(groups = {ShortcutGroup.class, DetailsGroup.class})
    @PositiveOrZero(message = "Issues count cannot be negative")
    private Integer issuesCount;

    @NotNull(groups = {ShortcutGroup.class, DetailsGroup.class})
    private Long boardId;

    @NotNull(groups = {ShortcutGroup.class, PostPutGroup.class, DetailsGroup.class})
    private String boardName;

    @NotNull(groups = DetailsGroup.class)
    private List<@NotNull @Valid IssueDto> issues;

    public Board toEntity() {
        return Board.builder()
                .name(boardName)
                .project(project == null ? null : project.toEntity())
                .issues(issues == null ? null : issues.stream()
                        .map(IssueDto::toEntity)
                        .toList())
                .build();
    }
}
