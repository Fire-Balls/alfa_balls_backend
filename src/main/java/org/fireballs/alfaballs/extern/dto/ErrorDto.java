package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ErrorDto extends RepresentationModel<ErrorDto> {

        @Positive(message = "Status must be positive")
        private int status;

        @NotBlank(message = "Error message must not be blank")
        private String message;
}

