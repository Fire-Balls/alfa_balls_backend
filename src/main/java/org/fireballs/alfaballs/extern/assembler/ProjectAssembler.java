package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.app.service.IssueService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.fireballs.alfaballs.extern.dto.newdtos.ProjectDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ProjectAssembler extends RepresentationModelAssemblerSupport<Project, ProjectDto> {
    private final IssueAssembler issueAssembler;
    private final BoardAssembler boardAssembler;
    private final IssueService issueService;
    private final UserAssembler userAssembler;

    public ProjectAssembler(IssueAssembler issueAssembler, BoardAssembler boardAssembler,
                            IssueService issueService, UserAssembler userAssembler) {
        super(ProjectController.class, ProjectDto.class);
        this.issueAssembler = issueAssembler;
        this.boardAssembler = boardAssembler;
        this.issueService = issueService;
        this.userAssembler = userAssembler;
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

        // todo херня надо переделать, так как даже в шорткате происходит джоин
        dto.setIssues(issueService.getAllIssuesByProjectId(entity.getId()).stream()
                .map(issueAssembler::toModel)
                .toList());

        dto.setParticipants(entity.getUsers().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Project toEntity(ProjectDto projectDto) {
        return Project.builder()
                .id(projectDto.getProjectId())
                .name(projectDto.getProjectName())
                .code(projectDto.getProjectCode())
                .boards(projectDto.getKanbanBoards() == null ? new ArrayList<>() :
                        projectDto.getKanbanBoards().stream().map(boardAssembler::toEntity).toList())
                .users(projectDto.getParticipants() == null ? new HashSet<>() :
                        projectDto.getParticipants().stream().map(userAssembler::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
