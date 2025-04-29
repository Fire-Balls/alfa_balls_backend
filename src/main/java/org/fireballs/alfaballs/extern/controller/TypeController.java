package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.TypeService;
import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.extern.assembler.details.TypeAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.MessageDto;
import org.fireballs.alfaballs.extern.dto.newdtos.TypeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects/{projectId}/types")
@RequiredArgsConstructor
public class TypeController {
    private final TypeService typeService;
    private final TypeAssembler typeAssembler;

    @PostMapping
    public ResponseEntity<TypeDto> createType(@PathVariable("projectId") Long projectId,
                                              @Validated(PostPutGroup.class) @RequestBody TypeDto typeDto) {
        Type savedType = typeService.saveNewType(projectId, typeAssembler.toEntity(typeDto));
        return new ResponseEntity<>(typeAssembler.toModel(savedType), HttpStatus.CREATED);
    }

    @GetMapping("/{typeId}")
    public ResponseEntity<TypeDto> getType(@PathVariable("projectId") Long projectId,
                                           @PathVariable("typeId") Long typeId) {
        Type retrievedType = typeService.getTypeById((typeId));
        return ResponseEntity.ok(typeAssembler.toModel(retrievedType));
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<TypeDto> updateType(@PathVariable("projectId") Long projectId,
                                              @PathVariable("typeId") Long typeId,
                                              @Validated(PostPutGroup.class) @RequestBody TypeDto typeDto) {
        Type updatedType = typeService.updateType(typeId, typeAssembler.toEntity(typeDto));
        return ResponseEntity.ok(typeAssembler.toModel(updatedType));
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<MessageDto> deleteType(@PathVariable("projectId") Long projectId,
                                                 @PathVariable("typeId") Long typeId) {
        typeService.deleteType(typeId);
        return ResponseEntity.ok(new MessageDto("Type with ID " + typeId + " has been deleted"));

    }
}
