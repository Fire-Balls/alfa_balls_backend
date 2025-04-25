package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BoardDetailsAssembler extends RepresentationModelAssemblerSupport<Board, BoardDto.Details> {
    private final IssueAssembler issueAssembler;
    private final ProjectShortcutAssembler projectShortcutAssembler;

    public BoardDetailsAssembler(IssueAssembler issueAssembler,
                                 ProjectShortcutAssembler projectShortcutAssembler) {
        super(ProjectController.class, BoardDto.Details.class);
        this.issueAssembler = issueAssembler;
        this.projectShortcutAssembler = projectShortcutAssembler;
    }

    @Override
    public BoardDto.Details toModel(Board entity) {
        BoardDto.Details dto = instantiateModel(entity);

        dto.setBoardId(entity.getId());
        dto.setBoardName(entity.getName());
        dto.setProject(projectShortcutAssembler.toModel(entity.getProject()));
        dto.setIssuesCount(entity.getIssues().size());
        dto.setIssues(entity.getIssues().stream()
                .map(issueAssembler::toModel)
                .toList());

        return dto;
    }

    public Board toEntity(BoardDto.Details boardDto) {
        return Board.builder()
                .id(boardDto.getBoardId())
                .name(boardDto.getBoardName())
                .project(boardDto.getProject() == null ? null : projectShortcutAssembler.toEntity(boardDto.getProject()))
                .issues(boardDto.getIssues() == null ? null : boardDto.getIssues().stream()
                        .map(issueAssembler::toEntity)
                        .toList())
                .build();
    }
}
