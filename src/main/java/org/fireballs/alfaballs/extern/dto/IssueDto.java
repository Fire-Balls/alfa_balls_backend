package org.fireballs.alfaballs.extern.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.validation.IsAfter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface IssueDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    class CreateUpdate extends RepresentationModel<CreateUpdate> {
        @NotBlank
        private String title;

        private String description;

        @NotNull
        private Long authorId;

        @NotNull
        private Long assigneeId;

        @NotNull
        private String type;

        @NotNull
        private Long statusId;

        private LocalDateTime deadline;

        @NotNull
        private List<String> tags;

        private List<MultipartFile> files;
    }


    @EqualsAndHashCode(callSuper = true)
    @Data
    @IsAfter(afterField = "createdAt", beforeField = "deadline")
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        @PositiveOrZero
        private Long id;

        @NotBlank
        private String title;

        @NotNull
        private String type;

        @NotNull
        private StatusDto status;

        @NotNull
        private UserDto.Shortcut assignee;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotBlank
        @Pattern(regexp = "^[A-Z0-9_]{3}-[0-9]+$")
        private String code;

        @NotNull
        private List<String> tags;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @IsAfter(afterField = "createdAt", beforeField = "deadline")
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        @PositiveOrZero
        private Long id;

        @NotBlank
        private String title;

        @NotBlank
        private String description;

        @NotNull
        private String type;

        @NotNull
        private StatusDto status;

        @NotNull
        private UserDto.Shortcut assignee;

        @NotNull
        private UserDto.Shortcut author;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotBlank
        @Pattern(regexp = "^[A-Z0-9_]{3}-[0-9]+$")
        private String code;

        @NotNull
        private List<String> tags;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private LocalDateTime createdAt;

        private LocalDateTime deadline;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Set<UserDto.Shortcut> observers;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Set<IssueDto.Shortcut> depends;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private List<String> fileUrls;
    }
}
