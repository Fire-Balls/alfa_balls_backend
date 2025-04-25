package org.fireballs.alfaballs.extern.assembler;

import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.extern.controller.IssueController;
import org.fireballs.alfaballs.extern.dto.newdtos.IssueDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class IssueAssembler extends RepresentationModelAssemblerSupport<Issue, IssueDto> {
    public IssueAssembler() {
        super(IssueController.class, IssueDto.class);
    }

    //todo observers and dependencies
    @Override
    public IssueDto toModel(Issue entity) {
        IssueDto issueDto = instantiateModel(entity);

        issueDto.setId(entity.getId());
        issueDto.setTitle(entity.getTitle());
        issueDto.setProjectId(entity.getBoard().getProject().getId());
        issueDto.setType(String.valueOf(entity.getType()));
        issueDto.setStatus(String.valueOf(entity.getStatus()));
        issueDto.setAssignee(String.valueOf(entity.getAssignee()));

        // ...

        return issueDto;
    }

    //todo peredelat
    public Issue toEntity(IssueDto issueDto) {
        return new Issue();
    }
}
