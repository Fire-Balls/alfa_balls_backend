package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.ProjectRepository;
import org.fireballs.alfaballs.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        projectRepository.save(project);
        log.info("Project {} was created", project.getId());

        return project;
    }

    public Project getProjectById(long projectId) {
        var searchedGame = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + projectId + " not found"));
        log.info("Project {} was found", projectId);
        return searchedGame;
    }

    public void deleteProject(long projectId) {
        projectRepository.deleteById(projectId);
        log.info("Project {} was deleted", projectId);
    }

    public List<Project> getAllProjects() {
        log.info("Get all projects");
        return projectRepository.findAll();
    }
}
