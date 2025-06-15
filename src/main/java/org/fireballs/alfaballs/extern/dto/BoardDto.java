package org.fireballs.alfaballs.extern.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

public interface BoardDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long boardId;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        private String boardName;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private ProjectDto.Shortcut project;

        @NotNull
        @PositiveOrZero
        private Integer issuesCount;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long boardId;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        private String boardName;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private ProjectDto.Shortcut project;

        @NotNull
        @PositiveOrZero
        private Integer issuesCount;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<IssueDto.Shortcut> issues;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<StatusDto> statuses;
    }
}
