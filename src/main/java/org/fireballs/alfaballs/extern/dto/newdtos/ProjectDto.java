package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutView;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDto extends RepresentationModel<ProjectDto> {
    @JsonView({DetailsView.class, ShortcutView.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    Long projectId;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotBlank(groups = PostPutGroup.class)
    @Size(max = 100, groups = PostPutGroup.class)
    String projectName;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotBlank(groups = PostPutGroup.class)
    @Size(min = 2, max = 10, groups = PostPutGroup.class)
    @Pattern(regexp = "^[A-Z0-9_]+$", groups = PostPutGroup.class)
    String projectCode;

    @JsonView(DetailsView.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private List<@Valid BoardDto> kanbanBoards;

    @JsonView(DetailsView.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private List<@Valid IssueDto> issues;

    @JsonView(DetailsView.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Set<UserDto> participants;
}
