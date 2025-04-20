package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.ProjectShortcutDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectShortcutAssembler extends RepresentationModelAssemblerSupport<Project, ProjectShortcutDto> {
    public ProjectShortcutAssembler() {
        super(ProjectController.class, ProjectShortcutDto.class);
    }

    @Override
    public ProjectShortcutDto toModel(Project entity) {
        ProjectShortcutDto dto = instantiateModel(entity);

        dto.setProjectId(entity.getId());
        dto.setProjectName(entity.getName());
        dto.setProjectCode(entity.getCode());

        return dto;
    }
}
