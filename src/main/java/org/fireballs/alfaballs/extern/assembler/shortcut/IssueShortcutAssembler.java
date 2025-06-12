package org.fireballs.alfaballs.extern.assembler.shortcut;

import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.extern.assembler.details.StatusAssembler;
import org.fireballs.alfaballs.extern.assembler.details.TypeAssembler;
import org.fireballs.alfaballs.extern.controller.IssueController;
import org.fireballs.alfaballs.extern.dto.IssueDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class IssueShortcutAssembler extends RepresentationModelAssemblerSupport<Issue, IssueDto.Shortcut> {
    private final TypeAssembler typeAssembler;
    private final StatusAssembler statusAssembler;
    private final UserShortcutAssembler userShortcutAssembler;

    public IssueShortcutAssembler(TypeAssembler typeAssembler,
                                  StatusAssembler statusAssembler,
                                  UserShortcutAssembler userShortcutAssembler) {
        super(IssueController.class, IssueDto.Shortcut.class);
        this.typeAssembler = typeAssembler;
        this.statusAssembler = statusAssembler;
        this.userShortcutAssembler = userShortcutAssembler;
    }

    @Override
    public IssueDto.Shortcut toModel(Issue entity) {
        IssueDto.Shortcut shortcut = instantiateModel(entity);

        shortcut.setId(entity.getId());
        shortcut.setTitle(entity.getTitle());
        shortcut.setCode(entity.getCode());
        shortcut.setType(typeAssembler.toModel(entity.getType()));
        shortcut.setStatus(statusAssembler.toModel(entity.getStatus()));
        shortcut.setAssignee(entity.getAssignee() == null ? null : userShortcutAssembler.toModel(entity.getAssignee()));
        shortcut.setTags(entity.getTags());

        return shortcut;
    }

    public Issue toEntity(IssueDto.Shortcut dto) {
        return Issue.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .code(dto.getCode())
                .assignee(dto.getAssignee() == null ? null : userShortcutAssembler.toEntity(dto.getAssignee()))
                .status(dto.getStatus() == null ? null : statusAssembler.toEntity(dto.getStatus()))
                .type(dto.getType() == null ? null : typeAssembler.toEntity(dto.getType()))
                .tags(dto.getTags())
                .build();
    }
}
