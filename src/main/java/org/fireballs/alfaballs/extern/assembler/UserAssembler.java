package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.User;
import org.fireballs.alfaballs.extern.controller.UserController;
import org.fireballs.alfaballs.extern.dto.newdtos.ProjectDto;
import org.fireballs.alfaballs.extern.dto.newdtos.UserDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<User, UserDto> {
    private final ProjectService projectService;

    public UserAssembler(ProjectService projectService) {
        super(UserController.class, UserDto.class);
        this.projectService = projectService;
    }

    @Override
    public UserDto toModel(User entity) {
        UserDto userDto = instantiateModel(entity);

        userDto.setId(entity.getId());
        userDto.setFullName(entity.getName());
        userDto.setEmail(entity.getEmail());
        //userDto.setRoles(entity.getRole());
        userDto.setAvatar(Base64.getEncoder().encodeToString(entity.getAvatar()));

        userDto.setProjects(entity.getProjects().stream()
                .map(Project::getId)
                .collect(Collectors.toSet()));

        return userDto;
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getFullName())
                .email(userDto.getEmail())
                .avatar(Base64.getDecoder().decode(userDto.getAvatar()))
                .projects(userDto.getProjects() == null ? new HashSet<>() :
                        userDto.getProjects().stream().map(projectService::getProjectById).collect(Collectors.toSet()))
                .build();
    }
}
