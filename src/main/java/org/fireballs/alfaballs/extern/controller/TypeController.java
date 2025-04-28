package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.TypeService;
import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.extern.assembler.TypeAssembler;
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

        return new ResponseEntity<>(typeAssembler.toModel(retrievedType), HttpStatus.OK);
    }

    @PutMapping("/{typeId}")
    public ResponseEntity<TypeDto> updateType(@PathVariable("projectId") Long projectId,
                                              @PathVariable("typeId") Long typeId,
                                              @Validated(PostPutGroup.class) @RequestBody TypeDto typeDto) {
        Type updatedType = typeService.updateType(typeId, typeAssembler.toEntity(typeDto));
        return new ResponseEntity<>(typeAssembler.toModel(updatedType), HttpStatus.OK);
    }

    @DeleteMapping("/{typeId}")
    public ResponseEntity<MessageDto> deleteType(@PathVariable("projectId") Long projectId,
                                                 @PathVariable("typeId") Long typeId) {
        typeService.deleteType(typeId);

        MessageDto response = new MessageDto();
        response.setMessage("Type with ID " + typeId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
