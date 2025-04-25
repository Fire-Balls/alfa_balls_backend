package org.fireballs.alfaballs.extern.dto.newdtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutView;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends RepresentationModel<UserDto> {
    @JsonView({DetailsView.class})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Long id;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotNull(groups = PostPutGroup.class)
    @Size(min = 1, max = 255)
    private String fullName;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotNull(groups = PostPutGroup.class)
    @Email
    private String email;

    @JsonView({DetailsView.class, ShortcutView.class})
    @NotNull(groups = PostPutGroup.class)
    private String avatar;

    @JsonView(DetailsView.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Long> projects;
}

//    @NotNull(groups = {PostPutGroup.class, DetailsView.class})
//    @NotEmpty
//    private List<@NotBlank @Size(max = 100) String> roles;}