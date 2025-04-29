package org.fireballs.alfaballs.extern.assembler.details;

import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.extern.assembler.shortcut.IssueShortcutAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.UserShortcutAssembler;
import org.fireballs.alfaballs.extern.controller.IssueController;
import org.fireballs.alfaballs.extern.dto.newdtos.IssueDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class IssueDetailsAssembler extends RepresentationModelAssemblerSupport<Issue, IssueDto.Details> {
    private final TypeAssembler typeAssembler;
    private final StatusAssembler statusAssembler;
    private final UserShortcutAssembler userShortcutAssembler;
    private final IssueShortcutAssembler issueShortcutAssembler;

    public IssueDetailsAssembler(TypeAssembler typeAssembler, StatusAssembler statusAssembler, UserShortcutAssembler userShortcutAssembler, IssueShortcutAssembler issueShortcutAssembler) {
        super(IssueController.class, IssueDto.Details.class);
        this.typeAssembler = typeAssembler;
        this.statusAssembler = statusAssembler;
        this.userShortcutAssembler = userShortcutAssembler;
        this.issueShortcutAssembler = issueShortcutAssembler;
    }

    @Override
    public IssueDto.Details toModel(Issue entity) {
        IssueDto.Details details = instantiateModel(entity);

        details.setId(entity.getId());
        details.setTitle(entity.getTitle());
        details.setCode(entity.getCode());
        details.setTags(entity.getTags());

        details.setDescription(entity.getDescription());
        details.setCreatedAt(entity.getCreatedAt());
        details.setDeadline(entity.getDeadline());

        details.setType(typeAssembler.toModel(entity.getType()));
        details.setStatus(statusAssembler.toModel(entity.getStatus()));
        details.setAssignee(entity.getAssignee() == null ? null : userShortcutAssembler.toModel(entity.getAssignee()));
        details.setAuthor(userShortcutAssembler.toModel(entity.getAuthor()));

        details.setObservers(entity.getObservers().stream()
                .map(userShortcutAssembler::toModel)
                .collect(Collectors.toSet()));

        details.setDepends(entity.getDependencies().stream()
                .map(issueShortcutAssembler::toModel)
                .collect(Collectors.toSet()));

        return details;
    }

    public Issue toEntity(IssueDto.Details dto) {
        return Issue.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .code(dto.getCode())
                .tags(dto.getTags())
                .createdAt(dto.getCreatedAt())
                .deadline(dto.getDeadline())
                .description(dto.getDescription())
                .assignee(dto.getAssignee() == null ? null : userShortcutAssembler.toEntity(dto.getAssignee()))
                .status(dto.getStatus() == null ? null : statusAssembler.toEntity(dto.getStatus()))
                .type(dto.getType() == null ? null : typeAssembler.toEntity(dto.getType()))
                .observers(dto.getObservers() == null ? new HashSet<>() :
                        dto.getObservers().stream().map(userShortcutAssembler::toEntity).collect(Collectors.toSet()))
                .dependencies(dto.getDepends() == null ? new HashSet<>() :
                        dto.getDepends().stream().map(issueShortcutAssembler::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
