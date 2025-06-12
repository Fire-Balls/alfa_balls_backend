package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.IssueService;
import org.fireballs.alfaballs.app.service.StatusService;
import org.fireballs.alfaballs.app.service.TypeService;
import org.fireballs.alfaballs.app.service.UserService;
import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.extern.assembler.details.IssueDetailsAssembler;
import org.fireballs.alfaballs.extern.dto.IssueDto;
import org.fireballs.alfaballs.extern.dto.MessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/boards/{boardId}/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;
    private final IssueDetailsAssembler issueDetailsAssembler;
    private final UserService userService;
    private final TypeService typeService;
    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<IssueDto.Details> createIssue(@PathVariable long projectId,
                                                        @PathVariable long boardId,
                                                        @RequestBody IssueDto.CreateUpdate issueDto) {

        Issue savedIssue = issueService.saveIssue(boardId, postPutRequestToEntity(issueDto));
        return new ResponseEntity<>(issueDetailsAssembler.toModel(savedIssue), HttpStatus.CREATED);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDto.Details> getIssue(@PathVariable long projectId,
                                                     @PathVariable long boardId,
                                                     @PathVariable long issueId) {

        Issue retrievedIssue = issueService.getIssueById(issueId);
        return ResponseEntity.ok(issueDetailsAssembler.toModel(retrievedIssue));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<IssueDto.Details> updateIssue(@PathVariable long projectId,
                                                        @PathVariable long boardId,
                                                        @PathVariable long issueId,
                                                        @RequestBody IssueDto.CreateUpdate issueDto) {

        Issue updatedIssue = issueService.updateIssue(issueId, postPutRequestToEntity(issueDto));
        return ResponseEntity.ok(issueDetailsAssembler.toModel(updatedIssue));
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageDto> deleteIssue(@PathVariable long projectId,
                                                  @PathVariable long boardId,
                                                  @PathVariable long issueId) {

        issueService.deleteIssue(issueId);
        return ResponseEntity.ok(new MessageDto("Issue with ID " + issueId + " has been deleted"));
    }

    @PatchMapping("/{issueId}/observers/{userId}")
    public ResponseEntity<Void> addObserver(@PathVariable String boardId,
                                            @PathVariable String projectId,
                                            @PathVariable long issueId,
                                            @PathVariable long userId) {

        issueService.addObserverToIssue(issueId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{issueId}/observers/{userId}")
    public ResponseEntity<Void> removeObserver(@PathVariable String boardId,
                                               @PathVariable String projectId,
                                               @PathVariable long issueId,
                                               @PathVariable long userId) {
        issueService.removeObserverFromIssue(issueId, userId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{issueId}/dependencies/{dependsOnId}")
    public ResponseEntity<Void> addDependency(@PathVariable String boardId,
                                              @PathVariable String projectId,
                                              @PathVariable long issueId,
                                              @PathVariable long dependsOnId) {

        issueService.addDependency(issueId, dependsOnId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{issueId}/dependencies/{dependsOnId}")
    public ResponseEntity<Void> removeDependency(@PathVariable String boardId,
                                                 @PathVariable String projectId,
                                                 @PathVariable long issueId,
                                                 @PathVariable long dependsOnId) {

        issueService.removeDependency(issueId, dependsOnId);
        return ResponseEntity.ok().build();
    }

    private Issue postPutRequestToEntity(IssueDto.CreateUpdate dto) {
        return Issue.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .author(dto.getAuthorId() == null ? null : userService.getUserById(dto.getAuthorId()))
                .assignee(dto.getAssigneeId() == null ? null : userService.getUserById(dto.getAssigneeId()))
                .type(dto.getTypeId() == null ? null : typeService.getTypeById(dto.getTypeId()))
                .status(dto.getStatusId() == null ? null : statusService.getStatusById(dto.getStatusId()))
                .deadline(dto.getDeadline())
                .tags(dto.getTags())
                .build();
    }
}
