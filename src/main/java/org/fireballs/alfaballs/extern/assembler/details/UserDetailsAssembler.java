package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.domain.Membership;
import org.fireballs.alfaballs.extern.assembler.shortcut.ProjectShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.UserController;
import org.fireballs.alfaballs.extern.dto.UserDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class UserDetailsAssembler extends RepresentationModelAssemblerSupport<User, UserDto.Details> {
    private final ProjectShortcutAssembler projectShortcutAssembler;

    public UserDetailsAssembler(ProjectShortcutAssembler projectShortcutAssembler) {
        super(UserController.class, UserDto.Details.class);
        this.projectShortcutAssembler = projectShortcutAssembler;
    }

    @Override
    public UserDto.Details toModel(User entity) {
        UserDto.Details userDto = instantiateModel(entity);

        userDto.setId(entity.getId());
        userDto.setFullName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setRole(entity.getGlobalRole().toString());
        userDto.setAvatar(entity.getAvatar() == null ? null : Base64.getEncoder().encodeToString(entity.getAvatar()));

        userDto.setProjects(entity.getMemberships().stream()
                .map(Membership::getProject)
                .map(projectShortcutAssembler::toModel)
                .collect(Collectors.toSet()));

        return userDto;
    }

    public User toEntity(UserDto.Details userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getFullName())
                .email(userDto.getEmail())
                .avatar(userDto.getAvatar() == null ? null : Base64.getDecoder().decode(userDto.getAvatar()))
                .build();
    }
}
