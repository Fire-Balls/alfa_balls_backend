package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public interface BoardDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long boardId;

        @NotNull(groups = PostPutGroup.class)
        private String boardName;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private @Valid ProjectDto.Shortcut project;

        @JsonView
        @NotNull
        @PositiveOrZero
        private Integer issuesCount;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long boardId;

        @NotNull(groups = PostPutGroup.class)
        private String boardName;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private @Valid ProjectDto.Shortcut project;

        @NotNull
        @PositiveOrZero()
        private Integer issuesCount;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<@NotNull @Valid IssueDto> issues;

        //private List<@NotNull @Valid StatusDto> statuses;
    }
}
