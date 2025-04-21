package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import jakarta.validation.Valid;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ObserverDto extends RepresentationModel<ObserverDto> {

        @NotNull
        @Size(min = 1, max = 100)
        private String role;

        @NotNull
        @Valid
        private ProfileDto profile;
}
