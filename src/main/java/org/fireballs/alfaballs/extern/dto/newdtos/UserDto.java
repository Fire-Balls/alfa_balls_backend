package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

public interface UserDto {
    @EqualsAndHashCode(callSuper = true)
    @Data
    class Shortcut extends RepresentationModel<Shortcut> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long id;

        @NotNull(groups = {PostPutGroup.class, Default.class})
        @Size(min = 1, max = 255)
        private String fullName;

        @NotNull(groups = {PostPutGroup.class, Default.class})
        @Email
        private String email;

        @NotBlank
        private String role;

        private String avatar;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    class Details extends RepresentationModel<Details> {
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Long id;

        @NotNull(groups = {PostPutGroup.class, Default.class})
        @Size(min = 1, max = 255)
        private String fullName;

        @NotNull(groups = {PostPutGroup.class, Default.class})
        @Email
        private String email;

        private String avatar;

        @NotBlank
        private String role;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @NotNull
        private Set<ProjectDto.Shortcut> projects;
    }
}
