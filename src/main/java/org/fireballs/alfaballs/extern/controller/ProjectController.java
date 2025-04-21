package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.extern.assembler.ProjectShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.ProjectShortcutDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectShortcutAssembler projectShortcutAssembler;

    @GetMapping
    public ResponseEntity<List<ProjectShortcutDto>> getAllProjects() {
        List<ProjectShortcutDto> projects = projectService.getAllProjects().stream()
                .map(projectShortcutAssembler::toModel)
                .toList();
        return ResponseEntity.ok(projects);
    }
}
