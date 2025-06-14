package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.app.service.IssueService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.assembler.shortcut.BoardShortcutAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.IssueShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.ProjectDto;
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
    private final MembershipAssembler membershipAssembler;

    public ProjectDetailsAssembler(IssueShortcutAssembler issueShortcutAssembler,
                                   BoardShortcutAssembler boardAssembler,
                                   IssueService issueService,
                                   MembershipAssembler membershipAssembler) {
        super(ProjectController.class, ProjectDto.Details.class);
        this.issueShortcutAssembler = issueShortcutAssembler;
        this.boardShortcutAssembler = boardAssembler;
        this.issueService = issueService;
        this.membershipAssembler = membershipAssembler;
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

        dto.setParticipants(entity.getMemberships().stream()
                .map(membershipAssembler::toModel)
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
                .build();
    }
}
