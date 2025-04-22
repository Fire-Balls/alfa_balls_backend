package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.BoardService;
import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.assembler.BoardAssembler;
import org.fireballs.alfaballs.extern.assembler.ProjectAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.newdtos.BoardDto;
import org.fireballs.alfaballs.extern.dto.newdtos.ProjectDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectAssembler projectAssembler;

    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects().stream()
                .map(projectAssembler::toModel)
                .toList();
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Validated(PostPutGroup.class) @RequestBody ProjectDto projectDto) {
        Project savedProject = projectService.saveProject(projectDto.toEntity());
        return new ResponseEntity<>(projectAssembler.toModel(savedProject), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        Project retrievedProject = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectAssembler.toModel(retrievedProject), HttpStatus.OK);
    }
}
