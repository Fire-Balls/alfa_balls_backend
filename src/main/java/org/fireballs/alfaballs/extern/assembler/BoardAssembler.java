package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.extern.controller.ProjectController;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.springframework.context.annotation.Lazy;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class BoardAssembler extends RepresentationModelAssemblerSupport<Board, BoardDto> {
    private final ProjectAssembler projectAssembler;
    private final IssueAssembler issueAssembler;

    public BoardAssembler(@Lazy ProjectAssembler projectAssembler, IssueAssembler issueAssembler) {
        super(ProjectController.class, BoardDto.class);
        this.projectAssembler = projectAssembler;
        this.issueAssembler = issueAssembler;
    }

    @Override
    public BoardDto toModel(Board entity) {
        BoardDto dto = instantiateModel(entity);

        dto.setBoardId(entity.getId());
        dto.setBoardName(entity.getName());
        dto.setProject(projectAssembler.toModel(entity.getProject()));
        dto.setIssuesCount(entity.getIssues().size());
        dto.setIssues(entity.getIssues().stream()
                .map(issueAssembler::toModel)
                .toList());

        return dto;
    }
}
