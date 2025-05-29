//package org.fireballs.alfaballs.app.service;
//
//import jakarta.transaction.Transactional;
//import org.fireballs.alfaballs.app.repository.ProjectRepository;
//import org.fireballs.alfaballs.app.repository.TypeRepository;
//import org.fireballs.alfaballs.domain.Project;
//import org.fireballs.alfaballs.domain.User;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class ProjectServiceIntegrationTest {
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private TypeRepository typeRepository;
//
//    private Project project;
//
//    @BeforeEach
//    void setUp() {
//        project = new Project();
//        project.setName("Test Project");
//        project.setCode("TP-001");
//
//        project = projectService.saveProject(project);
//    }
//
//    @Test
//    void testSaveProject() {
//        assertNotNull(project.getId());
//        assertEquals("Test Project", project.getName());
//    }
//
//    @Test
//    void testGetProjectById() {
//        Project found = projectService.getProjectById(project.getId());
//        assertEquals(project.getName(), found.getName());
//    }
//
//    @Test
//    void testGetAllProjects() {
//        List<Project> projects = projectService.getAllProjects();
//        assertFalse(projects.isEmpty());
//    }
//
//    @Test
//    void testUpdateProject() {
//        Project updated = new Project();
//        updated.setName("Updated Name");
//        updated.setCode("UPD-001");
//
//        Project result = projectService.updateProject(project.getId(), updated);
//        assertEquals("Updated Name", result.getName());
//        assertEquals("UPD-001", result.getCode());
//    }
//
//    @Test
//    void testDeleteProject() {
//        projectService.deleteProject(project.getId());
//        assertThrows(IllegalArgumentException.class, () -> projectService.getProjectById(project.getId()));
//    }
//
//    @Test
//    void testAddAndRemoveUserToProject() {
//        User user = new User();
//        user.setName("Alice");
//        user.setEmail("alice@example.com");
//        user.setAvatar("avatar.png".getBytes());
//
//        user = userService.saveUser(user);
//
//        projectService.addUserToProject(project.getId(), user.getId());
//
//        Project updated = projectService.getProjectById(project.getId());
//        assertTrue(updated.getUsers().contains(user));
//
//        projectService.removeUserFromProject(project.getId(), user.getId());
//        Project afterRemoval = projectService.getProjectById(project.getId());
//        assertFalse(afterRemoval.getUsers().contains(user));
//    }
//}
