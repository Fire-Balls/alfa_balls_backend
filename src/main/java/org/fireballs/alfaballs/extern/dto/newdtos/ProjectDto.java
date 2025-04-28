package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

public interface ProjectDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long projectId;

        @NotBlank(groups = PostPutGroup.class)
        @Size(max = 100)
        String projectName;

        @NotBlank(groups = PostPutGroup.class)
        @Size(min = 2, max = 10)
        @Pattern(regexp = "^[A-Z0-9_]+$")
        String projectCode;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long projectId;

        @NotBlank(groups = PostPutGroup.class)
        @Size(max = 100)
        String projectName;

        @NotBlank(groups = PostPutGroup.class)
        @Size(min = 2, max = 10)
        @Pattern(regexp = "^[A-Z0-9_]+$")
        String projectCode;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<BoardDto.@Valid Shortcut> kanbanBoards;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<@Valid IssueDto> issues;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Set<UserDto.@Valid Shortcut> participants;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private @Valid Set<TypeDto> types;
    }
}
