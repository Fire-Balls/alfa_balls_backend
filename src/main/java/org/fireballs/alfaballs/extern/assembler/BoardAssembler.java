package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.fireballs.alfaballs.extern.dto.newdtos.IssueDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BoardAssembler extends RepresentationModelAssemblerSupport<Board, BoardDto> {
    private final IssueAssembler issueAssembler;
    private final ProjectService projectService;

    public BoardAssembler(@Lazy ProjectAssembler projectAssembler, IssueAssembler issueAssembler, ProjectService projectService) {
        super(ProjectController.class, BoardDto.class);
        this.issueAssembler = issueAssembler;
        this.projectService = projectService;
    }

    @Override
    public BoardDto toModel(Board entity) {
        BoardDto dto = instantiateModel(entity);

        dto.setBoardId(entity.getId());
        dto.setBoardName(entity.getName());
        dto.setProjectId(entity.getProject().getId());
        dto.setIssuesCount(entity.getIssues().size());
        dto.setIssues(entity.getIssues().stream()
                .map(issueAssembler::toModel)
                .toList());

        return dto;
    }

    public Board toEntity(BoardDto boardDto) {
        return Board.builder()
                .name(boardDto.getBoardName())
                .project(boardDto.getProjectId() == null ? null : projectService.getProjectById(boardDto.getProjectId()))
                .issues(boardDto.getIssues() == null ? null : boardDto.getIssues().stream()
                        .map(IssueDto::toEntity)
                        .toList())
                .build();
    }
}
