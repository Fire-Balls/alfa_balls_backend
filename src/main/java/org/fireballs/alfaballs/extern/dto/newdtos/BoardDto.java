package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutView;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto extends RepresentationModel<BoardDto> {
    @JsonView({DetailsView.class, ShortcutView.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Long boardId;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotNull(groups = PostPutGroup.class)
    private String boardName;

    @JsonView({DetailsView.class, ShortcutView.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Long projectId;

    @JsonView(ShortcutView.class)
    @NotNull
    @PositiveOrZero()
    private Integer issuesCount;

    @JsonView(DetailsView.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull(groups = DetailsView.class)
    private List<@NotNull @Valid IssueDto> issues;
}
