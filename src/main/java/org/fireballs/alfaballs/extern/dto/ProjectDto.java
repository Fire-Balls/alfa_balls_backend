package org.fireballs.alfaballs.extern.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Set;

public interface ProjectDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long projectId;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        @Size(max = 100)
        String projectName;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        @Size(min = 2, max = 10)
        @Pattern(regexp = "^[A-Z0-9_]+$")
        String projectCode;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        Long projectId;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        @Size(max = 100)
        String projectName;

        @NotBlank(groups = {PostPutGroup.class, Default.class})
        @Pattern(regexp = "^[A-Z0-9_]{3}$")
        String projectCode;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<BoardDto.Shortcut> kanbanBoards;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private List<IssueDto.Shortcut> issues;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Set<MembershipDto> participants;
    }
}
