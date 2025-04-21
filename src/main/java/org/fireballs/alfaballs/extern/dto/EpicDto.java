package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class EpicDto extends RepresentationModel<EpicDto> {

        @NotNull
        private Long id;

        @NotNull
        @Size(min = 1, max = 255)
        private String title;

        @NotNull
        private List<@Valid FieldDto> fields;

        @NotNull
        private List<@Valid IssueShortcutDto> depends;

        @NotNull
        private List<@Valid ObserverDto> observers;

        @NotNull
        private List<@Valid IssueShortcutDto> tasks;
}

