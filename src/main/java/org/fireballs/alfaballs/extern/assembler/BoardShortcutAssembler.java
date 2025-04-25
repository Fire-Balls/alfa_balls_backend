package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BoardShortcutAssembler extends RepresentationModelAssemblerSupport<Board, BoardDto.Shortcut> {
    private final ProjectShortcutAssembler projectShortcutAssembler;

    public BoardShortcutAssembler(ProjectShortcutAssembler projectShortcutAssembler) {
        super(ProjectController.class, BoardDto.Shortcut.class);
        this.projectShortcutAssembler = projectShortcutAssembler;
    }

    @Override
    public BoardDto.Shortcut toModel(Board entity) {
        BoardDto.Shortcut dto = instantiateModel(entity);

        dto.setBoardId(entity.getId());
        dto.setBoardName(entity.getName());
        dto.setProject(projectShortcutAssembler.toModel(entity.getProject()));
        dto.setIssuesCount(entity.getIssues().size());

        return dto;
    }

    public Board toEntity(BoardDto.Shortcut boardDto) {
        return Board.builder()
                .id(boardDto.getBoardId())
                .name(boardDto.getBoardName())
                .project(boardDto.getProject() == null ? null : projectShortcutAssembler.toEntity(boardDto.getProject()))
                .build();
    }
}
