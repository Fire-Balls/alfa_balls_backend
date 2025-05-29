package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.exception.NotFoundException;
import org.fireballs.alfaballs.app.repository.ProjectRepository;
import org.fireballs.alfaballs.app.repository.TypeRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TypeRepository typeRepository;
    private final UserService userService;

    public Project saveProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        Project savedProject = projectRepository.save(project);
        log.info("Project {} was saved", project.getId());

        if (project.getTypes().isEmpty()) {
            Set<Type> defaultTypes = new HashSet<>() {{
                add(new Type(null, "Bug", savedProject, true));
                add(new Type(null, "Story", savedProject, true));
                add(new Type(null, "Task", savedProject, true));
            }};

            typeRepository.saveAll(defaultTypes);
            savedProject.setTypes(defaultTypes);
        }

        return savedProject;
    }

    public Project updateProject(long existingProjectId, Project newProject) {
        if (newProject == null) {
            throw new NotFoundException("New project is null");
        }

        Project existingProject = getProjectById(existingProjectId);

        existingProject.setName(newProject.getName());
        //todo issuecode probably doesnt change
        existingProject.setCode(newProject.getCode());

        return saveProject(existingProject);
    }

    public Project getProjectById(long projectId) {
        var searchedGame = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id " + projectId + " not found"));
        log.info("Project {} was found", projectId);
        return searchedGame;
    }

    public void deleteProject(long projectId) {
        Project project = getProjectById(projectId);

        // Удаляем проект из всех пользователей
        for (User user : project.getUsers()) {
            user.getProjects().remove(project);
        }
        project.getUsers().clear(); // тоже обнуляем с его стороны

        projectRepository.delete(project);
        log.info("Project {} was deleted", projectId);
    }

    public List<Project> getAllProjects() {
        log.info("Get all projects");
        return projectRepository.findAll();
    }

    public void addUserToProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.getUserById(userId);

        project.getUsers().add(user);
        user.getProjects().add(project);

        saveProject(project);
        log.info("User {} added to project {}", userId, project.getId());
    }

    public void removeUserFromProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.getUserById(userId);

        project.getUsers().remove(user);
        user.getProjects().remove(project);

        saveProject(project);
        log.info("User {} removed from project {}", userId, project.getId());
    }
}
