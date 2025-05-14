package org.fireballs.alfaballs.extern.dto.newdtos.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.newdtos.StatusDto;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegistrationRequestDto extends RepresentationModel<StatusDto> {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    @Pattern(regexp = "^USER|ADMIN$")
    private String globalRole;
}
