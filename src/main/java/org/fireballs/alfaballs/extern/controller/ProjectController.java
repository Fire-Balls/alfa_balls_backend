package org.fireballs.alfaballs.extern.controller;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.assembler.details.ProjectDetailsAssembler;
import org.fireballs.alfaballs.extern.assembler.shortcut.ProjectShortcutAssembler;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.MessageDto;
import org.fireballs.alfaballs.extern.dto.ProjectDto;
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
    private final ProjectDetailsAssembler projectDetailsAssembler;
    private final ProjectShortcutAssembler projectShortcutAssembler;

    @GetMapping
    public ResponseEntity<List<ProjectDto.Shortcut>> getAllProjects() {
        List<ProjectDto.Shortcut> projects = projectService.getAllProjects().stream()
                .map(projectShortcutAssembler::toModel)
                .toList();
        return ResponseEntity.ok(projects);
    }

    @PostMapping
    public ResponseEntity<ProjectDto.Shortcut> createProject(@Validated(PostPutGroup.class) @RequestBody ProjectDto.Shortcut projectDto) {
        Project savedProject = projectService.saveProject(projectShortcutAssembler.toEntity(projectDto));
        return new ResponseEntity<>(projectShortcutAssembler.toModel(savedProject), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto.Shortcut> getProjectById(@PathVariable Long projectId) {
        Project retrievedProject = projectService.getProjectById(projectId);
        return ResponseEntity.ok(projectShortcutAssembler.toModel(retrievedProject));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto.Shortcut> updateProjectById(@PathVariable Long projectId,
                                                        @Validated(PostPutGroup.class) @RequestBody ProjectDto.Shortcut projectDto) {
        Project updatedProject = projectService.updateProject(projectId, projectShortcutAssembler.toEntity(projectDto));
        return ResponseEntity.ok(projectShortcutAssembler.toModel(updatedProject));
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageDto> deleteProjectById(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok(new MessageDto("Project with ID " + projectId + " has been deleted"));

    }

    @GetMapping("/{projectId}/full")
    public ResponseEntity<ProjectDto.Details> getProjectDetailsById(@PathVariable Long projectId) {
        Project retrievedProject = projectService.getProjectById(projectId);
        return ResponseEntity.ok(projectDetailsAssembler.toModel(retrievedProject));
    }

    @PatchMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Void> addUserToProject(@PathVariable Long projectId, @PathVariable Long userId, @RequestParam String role) {
        projectService.addUserToProject(projectId, userId, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.removeUserFromProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{projectId}/users/{userId}/invite/send")
    public ResponseEntity<Void> sendInvite(@PathVariable Long projectId, @PathVariable Long userId, @RequestParam String role) {
        projectService.sendInvite(projectId, userId, role);
        return ResponseEntity.ok().build();
    }

    @GetMapping("invite/accept")
    public ResponseEntity<Void> acceptInvite(@RequestParam String token) {
        projectService.acceptInvite(token);
        return ResponseEntity.ok().build();
    }
}
