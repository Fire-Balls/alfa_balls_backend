package org.fireballs.alfaballs.extern.dto.newdtos.security;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class TokenResponseDto extends RepresentationModel<TokenResponseDto> {

        @NotBlank
        private String accessToken;

//        @NotBlank
//        private String refreshToken;
}

