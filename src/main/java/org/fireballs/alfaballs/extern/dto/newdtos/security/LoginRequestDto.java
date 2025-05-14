package org.fireballs.alfaballs.extern.dto.newdtos.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginRequestDto extends RepresentationModel<LoginRequestDto> {
        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;
}
