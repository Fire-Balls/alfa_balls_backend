package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.StatusService;
import org.fireballs.alfaballs.domain.Status;
import org.fireballs.alfaballs.extern.assembler.details.StatusAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.MessageDto;
import org.fireballs.alfaballs.extern.dto.StatusDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/boards/{boardId}/statuses")
@RequiredArgsConstructor
public class StatusController {
    private final StatusAssembler statusAssembler;
    private final StatusService statusService;

    @PostMapping
    public ResponseEntity<StatusDto> createStatus(@PathVariable("projectId") Long projectId,
                                                  @PathVariable("boardId") Long boardId,
                                                  @Validated(PostPutGroup.class) @RequestBody StatusDto statusDto) {
        Status savedStatus = statusService.saveNewStatus(boardId, statusAssembler.toEntity(statusDto));
        return new ResponseEntity<>(statusAssembler.toModel(savedStatus), HttpStatus.CREATED);
    }

    @GetMapping("/{statusId}")
    public ResponseEntity<StatusDto> getStatus(@PathVariable("projectId") Long projectId,
                                               @PathVariable("boardId") Long boardId,
                                               @PathVariable("statusId") Long statusId) {
        Status retrievedStatus = statusService.getStatusById((statusId));

        return ResponseEntity.ok(statusAssembler.toModel(retrievedStatus));
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<StatusDto> updateStatus(@PathVariable("projectId") Long projectId,
                                                @PathVariable("boardId") Long boardId,
                                                @PathVariable("statusId") Long statusId,
                                                @Validated(PostPutGroup.class) @RequestBody StatusDto statusDto) {
        Status updatedStatus = statusService.updateStatus(statusId, statusAssembler.toEntity(statusDto));
        return ResponseEntity.ok(statusAssembler.toModel(updatedStatus));
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<MessageDto> deleteStatus(@PathVariable("projectId") Long projectId,
                                                 @PathVariable("boardId") Long boardId,
                                                 @PathVariable("statusId") Long statusId) {
        statusService.deleteStatus(statusId);
        return ResponseEntity.ok(new MessageDto("Status with ID " + statusId + " has been deleted"));

    }
}
