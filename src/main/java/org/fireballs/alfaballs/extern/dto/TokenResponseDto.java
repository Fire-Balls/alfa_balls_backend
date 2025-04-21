package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class TokenResponseDto extends RepresentationModel<TokenResponseDto> {

        @NotBlank(message = "Access token is required")
        private String accessToken;

        @NotBlank(message = "Refresh token is required")
        private String refreshToken;
}

