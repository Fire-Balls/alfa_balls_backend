package org.fireballs.alfaballs.extern.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@EqualsAndHashCode(callSuper = true)
@Data
public class MembershipDto extends RepresentationModel<MembershipDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Long id;

    @NotNull
    private String fullName;

    @NotNull
    private String email;

    private String avatar;

    @NotNull
    private String role; // роль в проекте
}
