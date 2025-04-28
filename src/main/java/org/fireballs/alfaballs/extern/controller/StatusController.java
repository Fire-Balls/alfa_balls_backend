package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.StatusService;
import org.fireballs.alfaballs.domain.Status;
import org.fireballs.alfaballs.extern.assembler.StatusAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.MessageDto;
import org.fireballs.alfaballs.extern.dto.newdtos.StatusDto;
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

        return new ResponseEntity<>(statusAssembler.toModel(retrievedStatus), HttpStatus.OK);
    }

    @PutMapping("/{statusId}")
    public ResponseEntity<StatusDto> updateType(@PathVariable("projectId") Long projectId,
                                                @PathVariable("boardId") Long boardId,
                                                @PathVariable("statusId") Long statusId,
                                                @Validated(PostPutGroup.class) @RequestBody StatusDto statusDto) {
        Status existingStatus = statusService.getStatusById((statusId));
        existingStatus.setName(statusDto.getName());

        Status updatedStatus = statusService.saveExistingStatus(existingStatus);
        return new ResponseEntity<>(statusAssembler.toModel(updatedStatus), HttpStatus.OK);
    }

    @DeleteMapping("/{statusId}")
    public ResponseEntity<MessageDto> deleteType(@PathVariable("projectId") Long projectId,
                                                 @PathVariable("boardId") Long boardId,
                                                 @PathVariable("statusId") Long statusId) {
        statusService.deleteStatus(statusId);

        MessageDto response = new MessageDto();
        response.setMessage("Status with ID " + statusId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
