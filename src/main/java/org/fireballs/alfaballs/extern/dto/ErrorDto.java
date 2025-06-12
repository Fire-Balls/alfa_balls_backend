package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorDto extends RepresentationModel<ErrorDto> {
        @NotBlank(message = "Error message must not be blank")
        private String message;
}

