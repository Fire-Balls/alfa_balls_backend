package org.fireballs.alfaballs.app.service;

import org.fireballs.alfaballs.app.repository.ProjectRepository;
import org.fireballs.alfaballs.app.repository.TypeRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private TypeRepository typeRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private User user;

    @BeforeEach
    void setUp() {
        project = Project.builder()
                .id(1L)
                .name("Test Project")
                .code("TP")
                .build();

        user = new User();
        user.setId(100L);
        user.setProjects(new HashSet<>());
    }

    @Test
    void saveProject_shouldPersistProject() {
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project saved = projectService.saveProject(project);

        assertEquals(project.getId(), saved.getId());
        verify(projectRepository).save(project);
    }

    @Test
    void saveProject_shouldThrowIfNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.saveProject(null));
    }

    @Test
    void updateProject_shouldUpdateFields() {
        Project updated = Project.builder().name("Updated").code("UPD").build();

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(projectRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Project result = projectService.updateProject(1L, updated);

        assertEquals("Updated", result.getName());
        assertEquals("UPD", result.getCode());
        verify(projectRepository).save(project);
    }

    @Test
    void updateProject_shouldThrowIfNewProjectNull() {
        assertThrows(IllegalArgumentException.class, () -> projectService.updateProject(1L, null));
    }

    @Test
    void getProjectById_shouldReturnProject() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));

        Project found = projectService.getProjectById(1L);

        assertEquals(project.getId(), found.getId());
        verify(projectRepository).findById(1L);
    }

    @Test
    void getProjectById_shouldThrowIfNotFound() {
        when(projectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> projectService.getProjectById(1L));
    }

    @Test
    void deleteProject_shouldCallRepositoryDelete() {
        projectService.deleteProject(1L);
        verify(projectRepository).deleteById(1L);
    }

    @Test
    void getAllProjects_shouldReturnAll() {
        List<Project> projects = List.of(project);
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(1, result.size());
        verify(projectRepository).findAll();
    }

    @Test
    void addUserToProject_shouldAddUser() {
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userService.getUserById(100L)).thenReturn(user);
        when(projectRepository.save(project)).thenReturn(project);

        projectService.addUserToProject(1L, 100L);

        assertTrue(project.getUsers().contains(user));
        assertTrue(user.getProjects().contains(project));
        verify(projectRepository).save(project);
    }

    @Test
    void removeUserFromProject_shouldRemoveUser() {
        project.getUsers().add(user);
        user.getProjects().add(project);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userService.getUserById(100L)).thenReturn(user);
        when(projectRepository.save(project)).thenReturn(project);

        projectService.removeUserFromProject(1L, 100L);

        assertFalse(project.getUsers().contains(user));
        assertFalse(user.getProjects().contains(project));
        verify(projectRepository).save(project);
    }
}
