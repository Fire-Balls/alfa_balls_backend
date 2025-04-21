package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class FieldDefinitionDto extends RepresentationModel<FieldDefinitionDto> {

        @NotNull
        private Long id;

        @NotNull
        @Size(min = 1, max = 100)
        private String name;

        @NotNull
        private String dataType;
}


