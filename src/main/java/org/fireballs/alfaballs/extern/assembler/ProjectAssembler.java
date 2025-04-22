package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.app.service.IssueService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.ProjectDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProjectAssembler extends RepresentationModelAssemblerSupport<Project, ProjectDto> {
    private final IssueAssembler issueAssembler;
    private final BoardAssembler boardAssembler;
    private final IssueService issueService;

    public ProjectAssembler(IssueAssembler issueAssembler, BoardAssembler boardAssembler, IssueService issueService) {
        super(ProjectController.class, ProjectDto.class);
        this.issueAssembler = issueAssembler;
        this.boardAssembler = boardAssembler;
        this.issueService = issueService;
    }

    @Override
    public ProjectDto toModel(Project entity) {
        ProjectDto dto = instantiateModel(entity);

        dto.setProjectId(entity.getId());
        dto.setProjectName(entity.getName());
        dto.setProjectCode(entity.getCode());
        dto.setKanbanBoards(entity.getBoards().stream()
                .map(boardAssembler::toModel)
                .toList());
        dto.setIssues(issueService.getAllIssuesByProjectId(entity.getId()).stream()
                .map(issueAssembler::toModel)
                .toList());

        return dto;
    }
}
