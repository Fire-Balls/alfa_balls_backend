package org.fireballs.alfaballs.extern.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import java.util.List;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserProfileDto extends RepresentationModel<UserProfileDto> {

        @NotNull
        private Integer id;

        @NotNull
        @Size(min = 1, max = 255)
        private String fullName;

        @NotNull
        @Email
        private String email;

        @NotNull
        private String avatar;

        @NotNull
        @NotEmpty
        private List<@NotBlank @Size(max = 100) String> roles;
}


