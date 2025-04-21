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
public class FieldDto extends RepresentationModel<FieldDto> {

        @NotNull
        @Size(min = 1, max = 100)
        private String fieldName;

        @NotNull
        @Size(min = 1, max = 1000)
        private String fieldValue;

        @NotNull
        private Boolean required;

        private String type; // допустим nullable
}


