//package org.fireballs.alfaballs.app.service;
//
//import org.fireballs.alfaballs.app.repository.*;
//import org.fireballs.alfaballs.domain.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class EpicServiceIntegrationTest {
//
//    @Autowired
//    private EpicService epicService;
//
//    @Autowired
//    private EpicRepository epicRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private StatusRepository statusRepository;
//
//    @Autowired
//    private TypeRepository typeRepository;
//
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    private Project project;
//    private User author;
//    private User assignee;
//    private Board board;
//    private Status status;
//    private Type type;
//
//    @BeforeEach
//    void setUp() {
//        project = projectRepository.save(Project.builder().name("Proj").code("PR").build());
//        author = userRepository.save(User.builder().name("Author").email("a@example.com").avatar("a.png".getBytes()).build());
//        assignee = userRepository.save(User.builder().name("Assignee").email("b@example.com").avatar("b.png".getBytes()).build());
//        board = boardRepository.save(Board.builder().name("Board 1").project(project).build());
//        status = statusRepository.save(Status.builder().name("To Do").board(board).build());
//        type = typeRepository.save(Type.builder().name("Bug").project(board.getProject()).build());
//    }
//
//    @Test
//    void saveEpicWithTasks_shouldSaveWithAllFieldsCorrectly() {
//        Epic epic = new Epic();
//        epic.setTitle("Main Epic");
//        epic.setDescription("Epic Description");
//        epic.setStatus(status);
//        epic.setType(type);
//        epic.setBoard(board);
//        epic.setAuthor(author);
//        epic.setAssignee(assignee);
//        epic.setPriority(Issue.Priority.HIGH);
//        epic.setCode("PR-1");
//
//        Issue task1 = Issue.builder()
//                .title("Task 1")
//                .description("Subtask 1")
//                .status(status)
//                .type(type)
//                .board(board)
//                .author(author)
//                .assignee(assignee)
//                .epic(epic)
//                .priority(Issue.Priority.LOW)
//                .code("PR-2")
//                .build();
//
//        Issue task2 = Issue.builder()
//                .title("Task 2")
//                .description("Subtask 2")
//                .status(status)
//                .type(type)
//                .board(board)
//                .author(author)
//                .assignee(assignee)
//                .epic(epic)
//                .priority(Issue.Priority.LOW)
//                .code("PR-3")
//                .build();
//
//        epic.setTasks(List.of(task1, task2));
//
//        Epic savedEpic = epicService.saveEpic(epic);
//
//        assertNotNull(savedEpic.getId());
//        assertEquals(2, savedEpic.getTasks().size());
//        assertTrue(savedEpic.getTasks().stream().anyMatch(t -> t.getTitle().equals("Task 1")));
//    }
//
//    @Test
//    void getEpicById_shouldReturnSavedEpic() {
//        Epic epic = new Epic();
//        epic.setTitle("Retrieve Epic");
//        epic.setDescription("Description");
//        epic.setStatus(status);
//        epic.setType(type);
//        epic.setBoard(board);
//        epic.setAuthor(author);
//        epic.setAssignee(assignee);
//        epic.setPriority(Issue.Priority.HIGH);
//        epic.setCode("PR-4");
//
//        Epic saved = epicService.saveEpic(epic);
//
//        Epic found = epicService.getEpicById(saved.getId());
//
//        assertEquals(saved.getId(), found.getId());
//        assertEquals("Retrieve Epic", found.getTitle());
//    }
//
//    @Test
//    void deleteEpic_shouldRemoveFromDatabase() {
//        Epic epic = new Epic();
//        epic.setTitle("To be deleted");
//        epic.setDescription("Delete me");
//        epic.setStatus(status);
//        epic.setType(type);
//        epic.setBoard(board);
//        epic.setAuthor(author);
//        epic.setAssignee(assignee);
//        epic.setPriority(Issue.Priority.LOW);
//        epic.setCode("PR-5");
//
//        Epic saved = epicService.saveEpic(epic);
//
//        epicService.deleteEpic(saved.getId());
//
//        assertFalse(epicRepository.findById(saved.getId()).isPresent());
//    }
//
//    @Test
//    void getAllEpics_shouldReturnAllSavedEpics() {
//        epicService.saveEpic(buildEpic("E1", "PR-6"));
//        epicService.saveEpic(buildEpic("E2", "PR-7"));
//
//        List<Epic> epics = epicService.getAllEpics();
//
//        assertTrue(epics.size() >= 2);
//    }
//
//    private Epic buildEpic(String title, String code) {
//        Epic epic = new Epic();
//        epic.setTitle(title);
//        epic.setDescription("Test");
//        epic.setStatus(status);
//        epic.setType(type);
//        epic.setBoard(board);
//        epic.setAuthor(author);
//        epic.setAssignee(assignee);
//        epic.setPriority(Issue.Priority.HIGH);
//        epic.setCode(code);
//        return epic;
//    }
//}
