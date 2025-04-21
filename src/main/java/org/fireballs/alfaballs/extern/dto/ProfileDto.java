package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProfileDto extends RepresentationModel<ProfileDto> {

        @NotNull
        private String id;

        @NotNull
        @Size(min = 1, max = 255)
        private String fullName;

        @NotNull
        @Email
        private String email;

        @NotNull
        private String avatar;
}


