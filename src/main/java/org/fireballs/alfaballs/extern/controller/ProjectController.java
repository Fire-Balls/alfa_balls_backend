package org.fireballs.alfaballs.extern.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.service.ProjectService;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.extern.assembler.ProjectAssembler;
import org.fireballs.alfaballs.extern.dto.group.DetailsView;
import org.fireballs.alfaballs.extern.dto.group.PostPutGroup;
import org.fireballs.alfaballs.extern.dto.group.ShortcutView;
import org.fireballs.alfaballs.extern.dto.newdtos.MessageDto;
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

    @JsonView(ShortcutView.class)
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Validated(PostPutGroup.class) @RequestBody ProjectDto projectDto) {
        Project savedProject = projectService.saveProject(projectAssembler.toEntity(projectDto));
        return new ResponseEntity<>(projectAssembler.toModel(savedProject), HttpStatus.CREATED);
    }

    @JsonView(ShortcutView.class)
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long projectId) {
        Project retrievedProject = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectAssembler.toModel(retrievedProject), HttpStatus.OK);
    }

    @JsonView(ShortcutView.class)
    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProjectById(@PathVariable Long projectId,
                                                        @Validated(PostPutGroup.class) @RequestBody ProjectDto projectDto) {
        Project existingProject = projectService.getProjectById(projectId);
        existingProject.setName(projectDto.getProjectName());
        existingProject.setCode(projectDto.getProjectCode());

        Project updatedProject = projectService.saveProject(existingProject);
        return new ResponseEntity<>(projectAssembler.toModel(updatedProject), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageDto> deleteProjectById(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);

        MessageDto response = new MessageDto();
        response.setMessage("Project with ID " + projectId + " has been deleted");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @JsonView(DetailsView.class)
    @GetMapping("/{projectId}/full")
    public ResponseEntity<ProjectDto> getProjectDetailsById(@PathVariable Long projectId) {
        Project retrievedProject = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectAssembler.toModel(retrievedProject), HttpStatus.OK);
    }

    @PutMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Void> addUserToProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.addUserToProject(projectId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromProject(@PathVariable Long projectId, @PathVariable Long userId) {
        projectService.removeUserFromProject(projectId, userId);
        return ResponseEntity.noContent().build();
    }
}
