package org.fireballs.alfaballs.extern.dto.security;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefreshTokenDto extends RepresentationModel<RefreshTokenDto> {
    @NotBlank
    private String refreshToken;
}
