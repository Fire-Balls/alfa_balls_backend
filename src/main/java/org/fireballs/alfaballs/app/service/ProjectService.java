package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.ProjectRepository;
import org.fireballs.alfaballs.app.repository.TypeRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Type;
import org.fireballs.alfaballs.domain.User;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Project saveProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        Project savedProject = projectRepository.save(project);
        log.info("Project {} was saved", project.getId());

        //todo переделать, тк сейчас каждый раз сохраняется каждый дефолтный тип по новому.
        // сейчас сделано для отладки эндпоинта
//        Set<Type> defaultTypes = Set.of(
//                new Type(null, "Bug", savedProject),
//                new Type(null, "Story", savedProject),
//                new Type(null, "Feature", savedProject)
//        );

//        typeRepository.saveAll(defaultTypes);
//        project.setTypes(defaultTypes);

        return savedProject;
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

    @Transactional
    public void addUserToProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.getUserById(userId);

        project.getUsers().add(user);
        user.getProjects().add(project); // нужно для синхронизации bi-directional связи

        // Либо сохранение проекта, либо пользователя — в зависимости от настроек каскада
        saveProject(project);
    }

    @Transactional
    public void removeUserFromProject(Long projectId, Long userId) {
        Project project = getProjectById(projectId);
        User user = userService.getUserById(userId);

        project.getUsers().remove(user);
        user.getProjects().remove(project); // также синхронизируем обратную сторону

        saveProject(project);
    }
}
