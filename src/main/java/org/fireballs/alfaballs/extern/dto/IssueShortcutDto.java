package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class IssueShortcutDto extends RepresentationModel<IssueShortcutDto> {

        @NotNull(message = "ID is required")
        @Positive(message = "ID must be positive")
        private Long id;

        @NotNull(message = "Project ID is required")
        @Positive(message = "Project ID must be positive")
        private Long projectId;

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be less than 200 characters")
        private String title;

        @NotBlank(message = "Type is required")
        @Pattern(regexp = "^(BUG|TASK|FEATURE)$", message = "Type must be BUG, TASK or FEATURE")
        private String type;

        @NotBlank(message = "Status is required")
        @Pattern(regexp = "^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$", message = "Invalid status value")
        private String status;

        @NotBlank(message = "Assignee is required")
        @Email(message = "Assignee must be a valid email")
        private String assignee;
}

