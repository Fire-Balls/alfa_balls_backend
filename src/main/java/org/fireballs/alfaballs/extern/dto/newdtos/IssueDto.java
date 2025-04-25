package org.fireballs.alfaballs.extern.dto.newdtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.extern.dto.EpicShortcutDto;
import org.fireballs.alfaballs.extern.dto.FieldDto;
import org.fireballs.alfaballs.extern.dto.ObserverDto;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
import org.fireballs.alfaballs.extern.dto.group.ShortcutView;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class IssueDto extends RepresentationModel<IssueDto> {
    @NotNull(message = "ID is required", groups = {ShortcutView.class, DetailsView.class} )
    @Positive(message = "ID must be positive")
    private Long id;

    @NotNull(message = "Project ID is required", groups = ShortcutView.class)
    @Positive(message = "Project ID must be positive")
    private Long projectId;

    @NotBlank(message = "Title is required", groups = {ShortcutView.class, DetailsView.class})
    @Size(max = 200, message = "Title must be less than 200 characters")
    private String title;

    @NotBlank(message = "Type is required", groups = ShortcutView.class)
    @Pattern(regexp = "^(BUG|TASK|FEATURE)$", message = "Type must be BUG, TASK or FEATURE")
    private String type;

    @NotBlank(message = "Status is required", groups = ShortcutView.class)
    @Pattern(regexp = "^(OPEN|IN_PROGRESS|RESOLVED|CLOSED)$", message = "Invalid status value")
    private String status;

    @NotBlank(message = "Assignee is required", groups = ShortcutView.class)
    @Email(message = "Assignee must be a valid email")
    private String assignee;

    @NotNull(groups = DetailsView.class)
    private List<@Valid FieldDto> fields;

    @NotNull(groups = DetailsView.class)
    private List<@Valid IssueDto> depends;

    @NotNull(message = "Observers list required", groups = DetailsView.class)
    private List<@Valid ObserverDto> observers;

    @Valid
    @NotNull(groups = DetailsView.class)
    private EpicShortcutDto epic;

    public Issue toEntity() {
        return new Issue();
    }
}
