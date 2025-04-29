package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.assembler.shortcut.IssueShortcutAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.ProjectShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class BoardDetailsAssembler extends RepresentationModelAssemblerSupport<Board, BoardDto.Details> {
    private final IssueShortcutAssembler issueShortcutAssembler;
    private final ProjectShortcutAssembler projectShortcutAssembler;
    private final StatusAssembler statusAssembler;

    public BoardDetailsAssembler(IssueShortcutAssembler issueShortcutAssembler,
                                 ProjectShortcutAssembler projectShortcutAssembler, StatusAssembler statusAssembler) {
        super(ProjectController.class, BoardDto.Details.class);
        this.issueShortcutAssembler = issueShortcutAssembler;
        this.projectShortcutAssembler = projectShortcutAssembler;
        this.statusAssembler = statusAssembler;
    }

    @Override
    public BoardDto.Details toModel(Board entity) {
        BoardDto.Details dto = instantiateModel(entity);

        dto.setBoardId(entity.getId());
        dto.setBoardName(entity.getName());
        dto.setProject(projectShortcutAssembler.toModel(entity.getProject()));
        dto.setIssuesCount(entity.getIssues().size());
        dto.setIssues(entity.getIssues().stream()
                .map(issueShortcutAssembler::toModel)
                .toList());
        dto.setStatuses(entity.getStatuses().stream()
                .map(statusAssembler::toModel)
                .collect(Collectors.toSet()));

        return dto;
    }

    public Board toEntity(BoardDto.Details boardDto) {
        return Board.builder()
                .id(boardDto.getBoardId())
                .name(boardDto.getBoardName())
                .project(boardDto.getProject() == null ? null : projectShortcutAssembler.toEntity(boardDto.getProject()))
                .issues(boardDto.getIssues() == null ? new ArrayList<>() : boardDto.getIssues().stream()
                        .map(issueShortcutAssembler::toEntity)
                        .toList())
                .statuses(boardDto.getStatuses() == null ? new HashSet<>() : boardDto.getStatuses().stream()
                        .map(statusAssembler::toEntity)
                        .collect(Collectors.toSet()))
                .build();
    }
}
