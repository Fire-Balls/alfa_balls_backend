package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.app.service.IssueService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.assembler.shortcut.BoardShortcutAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.IssueShortcutAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.UserShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.ProjectDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class ProjectDetailsAssembler extends RepresentationModelAssemblerSupport<Project, ProjectDto.Details> {
    private final IssueShortcutAssembler issueShortcutAssembler;
    private final BoardShortcutAssembler boardShortcutAssembler;
    private final IssueService issueService;
    private final UserShortcutAssembler userShortcutAssembler;
    private final TypeAssembler typeAssembler;

    public ProjectDetailsAssembler(IssueShortcutAssembler issueShortcutAssembler,
                                   BoardShortcutAssembler boardAssembler,
                                   IssueService issueService,
                                   UserShortcutAssembler userShortcutAssembler, TypeAssembler typeAssembler) {
        super(ProjectController.class, ProjectDto.Details.class);
        this.issueShortcutAssembler = issueShortcutAssembler;
        this.boardShortcutAssembler = boardAssembler;
        this.issueService = issueService;
        this.userShortcutAssembler = userShortcutAssembler;
        this.typeAssembler = typeAssembler;
    }

    @Override
    public ProjectDto.Details toModel(Project entity) {
        ProjectDto.Details dto = instantiateModel(entity);

        dto.setProjectId(entity.getId());
        dto.setProjectName(entity.getName());
        dto.setProjectCode(entity.getCode());

        dto.setKanbanBoards(entity.getBoards().stream()
                .map(boardShortcutAssembler::toModel)
                .toList());

        dto.setIssues(issueService.getAllIssuesByProjectId(entity.getId()).stream()
                .map(issueShortcutAssembler::toModel)
                .toList());

        dto.setParticipants(entity.getUsers().stream()
                .map(userShortcutAssembler::toModel)
                .collect(Collectors.toSet()));

        dto.setTypes(entity.getTypes().stream()
                .map(typeAssembler::toModel)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Project toEntity(ProjectDto.Details projectDto) {
        return Project.builder()
                .id(projectDto.getProjectId())
                .name(projectDto.getProjectName())
                .code(projectDto.getProjectCode())
                .boards(projectDto.getKanbanBoards() == null ? new ArrayList<>() :
                        projectDto.getKanbanBoards().stream().map(boardShortcutAssembler::toEntity).toList())
                .users(projectDto.getParticipants() == null ? new HashSet<>() :
                        projectDto.getParticipants().stream().map(userShortcutAssembler::toEntity).collect(Collectors.toSet()))
                .types(projectDto.getTypes() == null ? new HashSet<>() :
                        projectDto.getTypes().stream().map(typeAssembler::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
