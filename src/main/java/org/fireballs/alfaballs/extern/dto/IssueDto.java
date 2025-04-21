package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class IssueDto extends RepresentationModel<IssueDto> {

        @NotNull(message = "ID is required")
        @Positive(message = "ID must be positive")
        private Long id;

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be less than 200 characters")
        private String title;

        @NotNull
        private List<@Valid FieldDto> fields;

        @NotNull
        private List<@Valid IssueShortcutDto> depends;

        @NotNull(message = "Observers list required")
        private List<@Valid ObserverDto> observers;

        @Valid
        @NotNull
        private EpicShortcutDto epic;
}


