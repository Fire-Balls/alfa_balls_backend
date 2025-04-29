package org.fireballs.alfaballs.extern.assembler.shortcut;

import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.controller.UserController;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashSet;

@Component
public class UserShortcutAssembler extends RepresentationModelAssemblerSupport<User, UserDto.Shortcut> {
    public UserShortcutAssembler() {
        super(UserController.class, UserDto.Shortcut.class);
    }

    @Override
    public UserDto.Shortcut toModel(User entity) {
        UserDto.Shortcut userDto = instantiateModel(entity);

        userDto.setId(entity.getId());
        userDto.setFullName(entity.getName());
        userDto.setEmail(entity.getEmail());
        //userDto.setRoles(entity.getRole());
        userDto.setAvatar(Base64.getEncoder().encodeToString(entity.getAvatar()));

        return userDto;
    }

    public User toEntity(UserDto.Shortcut userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getFullName())
                .email(userDto.getEmail())
                .avatar(Base64.getDecoder().decode(userDto.getAvatar()))
                .build();
    }
}
