//package org.fireballs.alfaballs.app.service;
//
//import jakarta.transaction.Transactional;
//import org.fireballs.alfaballs.domain.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class IssueServiceIntegrationTest {
//
//    @Autowired
//    private IssueService issueService;
//
//    @Autowired
//    private ProjectService projectService;
//
//    @Autowired
//    private BoardService boardService;
//
//    @Autowired
//    private UserService userService;
//
//    private Project project;
//    private Board board;
//    private Issue issue;
//
//    private User user;
//
//    @BeforeEach
//    void setUp() {
//        project = new Project();
//        project.setName("Alpha Project");
//        project.setCode("ALPHA");
//        project = projectService.saveProject(project);
//
//        board = new Board();
//        board.setName("Main Board");
//        board.setProject(project);
//        board = boardService.saveNewBoard(project.getId(), board);
//
//        user = new User();
//        user.setName("TestUser");
//        user.setEmail("test@example.com");
//        user.setAvatar("avatar.png".getBytes());
//        user = userService.saveUser(user);
//
//        //todo доделать
////        Issue issue = Issue.builder()
////                .title("Test Issue")
////                .description("Description")
////                .status()
////                .type()
////                .author(user)
////                .assignee(user)
////                .priority(Issue.Priority.HIGH)
////                .build();
//
//        issueService.saveNewIssue(board.getId(), issue);
//    }
//
//    @Test
//    void testSaveNewIssue() {
//        assertNotNull(issue.getId());
//        assertTrue(issue.getCode().startsWith("ALPHA-"));
//    }
//
//    @Test
//    void testUpdateIssue() {
//        issue.setTitle("Updated Issue");
//        Issue updated = issueService.updateIssue(issue);
//        assertEquals("Updated Issue", updated.getTitle());
//    }
//
//    @Test
//    void testGetIssueById() {
//        Issue found = issueService.getIssueById(issue.getId());
//        assertEquals(issue.getTitle(), found.getTitle());
//    }
//
//    @Test
//    void testDeleteIssue() {
//        issueService.deleteIssue(issue.getId());
//        assertThrows(IllegalArgumentException.class, () -> issueService.getIssueById(issue.getId()));
//    }
//
//    @Test
//    void testGetAllIssuesByProjectId() {
//        List<Issue> issues = issueService.getAllIssuesByProjectId(project.getId());
//        assertFalse(issues.isEmpty());
//    }
//
//    @Test
//    void testAddAndRemoveAssignee() {
//        issueService.addAssigneeToIssue(issue.getId(), user.getId());
//        Issue updated = issueService.getIssueById(issue.getId());
//        assertEquals(user.getId(), updated.getAssignee().getId());
//
//        issueService.removeAssigneeFromIssue(issue.getId(), user.getId());
//        updated = issueService.getIssueById(issue.getId());
//        assertNull(updated.getAssignee());
//    }
//
//    @Test
//    void testAddAndRemoveObserver() {
//        User observer = new User();
//        observer.setName("Observer");
//        observer.setEmail("observer@example.com");
//        observer.setAvatar("avatar.png".getBytes());
//        observer = userService.saveUser(observer);
//
//        issueService.addObserverToIssue(issue.getId(), observer.getId());
//        Issue updated = issueService.getIssueById(issue.getId());
//        assertTrue(updated.getObservers().contains(observer));
//
//        issueService.removeObserverFromIssue(issue.getId(), observer.getId());
//        updated = issueService.getIssueById(issue.getId());
//        assertFalse(updated.getObservers().contains(observer));
//    }
//
//    @Test
//    void testAddAndRemoveTag() {
//        String tag = "backend";
//        issueService.addTag(issue.getId(), tag);
//        assertTrue(issueService.getIssueById(issue.getId()).getTags().contains(tag));
//
//        issueService.removeTag(issue.getId(), tag);
//        assertFalse(issueService.getIssueById(issue.getId()).getTags().contains(tag));
//    }
//
//    @Test
//    void testModeDeadline() {
//        LocalDateTime future = LocalDateTime.now().plusDays(1);
//        issueService.modeDeadline(issue.getId(), future);
//        assertEquals(future, issueService.getIssueById(issue.getId()).getDeadline());
//    }
//
//    @Test
//    void testModeDeadlineThrowsException() {
//        LocalDateTime now = LocalDateTime.now();
//        issue.setDeadline(now.plusDays(2));
//        issueService.updateIssue(issue);
//
//        assertThrows(IllegalArgumentException.class, () -> issueService.modeDeadline(issue.getId(), now));
//    }
//}
