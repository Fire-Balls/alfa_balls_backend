package org.fireballs.alfaballs.extern.dto.newdtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.dto.group.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Base64;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends RepresentationModel<UserDto> {
    @NotNull(groups = {DetailsGroup.class, ShortcutGroup.class})
    private Long id;

    @NotNull(groups = {PostPutGroup.class, DetailsGroup.class, ShortcutGroup.class})
    @Size(min = 1, max = 255)
    private String fullName;

    @NotNull(groups = {PostPutGroup.class, DetailsGroup.class, ShortcutGroup.class})
    @Email
    private String email;

    @NotNull(groups = {PostPutGroup.class, DetailsGroup.class, ShortcutGroup.class})
    private String avatar;

    @NotNull(groups = {PostPutGroup.class, DetailsGroup.class})
    @NotEmpty
    private List<@NotBlank @Size(max = 100) String> roles;

    //todo одна роль или несколько, надо решить
    public User toEntity() {
        return User.builder()
                .id(id)
                .name(fullName)
                .email(email)
                .avatar(Base64.getDecoder().decode(avatar))
                //.role()
                .build();
    }
}
