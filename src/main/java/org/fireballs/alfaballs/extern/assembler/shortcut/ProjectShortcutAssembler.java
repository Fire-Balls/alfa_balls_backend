package org.fireballs.alfaballs.extern.assembler.shortcut;

import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.ProjectDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectShortcutAssembler extends RepresentationModelAssemblerSupport<Project, ProjectDto.Shortcut> {
    public ProjectShortcutAssembler() {
        super(ProjectController.class, ProjectDto.Shortcut.class);
    }

    @Override
    public ProjectDto.Shortcut toModel(Project entity) {
        ProjectDto.Shortcut dto = instantiateModel(entity);

        dto.setProjectId(entity.getId());
        dto.setProjectName(entity.getName());
        dto.setProjectCode(entity.getCode());

        return dto;
    }

    public Project toEntity(ProjectDto.Shortcut projectDto) {
        return Project.builder()
                .id(projectDto.getProjectId())
                .name(projectDto.getProjectName())
                .code(projectDto.getProjectCode())
                .build();
    }
}
