package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import org.fireballs.alfaballs.app.repository.*;
import org.fireballs.alfaballs.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@SpringJUnitConfig(classes = IssueFieldValueServiceIntegrationTest.Config.class)
@Transactional
public class IssueFieldValueServiceIntegrationTest {

    @TestConfiguration
    static class Config {
        @Bean
        public IssueFieldValueService issueFieldValueService(IssueFieldValueRepository repository) {
            return new IssueFieldValueService(repository);
        }
    }

    @Autowired
    private IssueFieldValueService service;

    @Autowired
    private IssueFieldValueRepository issueFieldValueRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TypeRepository typeRepository;
    @Autowired
    private EpicRepository epicRepository;
    @Autowired
    private IssueRepository issueRepository;
    @Autowired
    private FieldDefinitionRepository fieldDefinitionRepository;

    private Issue issue;
    private FieldDefinition field;

    @BeforeEach
    void setup() {
        User author = userRepository.save(User.builder().name("Author").email("author@test.com").avatar("a.png".getBytes()).build());
        User assignee = userRepository.save(User.builder().name("Assignee").email("assignee@test.com").avatar("b.png".getBytes()).build());

        Project project = projectRepository.save(Project.builder().name("Project").code("PRJ").build());
        Board board = boardRepository.save(Board.builder().name("Board").project(project).build());
        Status status = statusRepository.save(Status.builder().name("Open").board(board).build());
        Type type = typeRepository.save(Type.builder().name("Bug").project(project).build());

        Epic epic = new Epic();
        epic.setBoard(board);
        epic.setStatus(status);
        epic.setType(type);
        epic.setAssignee(assignee);
        epic.setAuthor(author);
        epic.setPriority(Issue.Priority.LOW);
        epic.setCode("PRJ-1");
        epicRepository.save(epic);

        issue = Issue.builder()
                .title("Issue")
                .description("Test")
                .board(board)
                .status(status)
                .type(type)
                .author(author)
                .assignee(assignee)
                .epic(epic)
                .priority(Issue.Priority.LOW)
                .code("PRJ-2")
                .build();
        issue = issueRepository.save(issue);

        field = fieldDefinitionRepository.save(new FieldDefinition(null, "Estimate", "String"));
    }

    @Test
    void testSaveAndFindById() {
        IssueFieldValueId id = new IssueFieldValueId(issue.getId(), field.getId());
        IssueFieldValue value = new IssueFieldValue(id, issue, field, "5d");

        service.save(value);
        IssueFieldValue fromDb = service.getById(id);

        assertNotNull(fromDb);
        assertEquals("5d", fromDb.getText());
    }

    @Test
    void testDelete() {
        IssueFieldValueId id = new IssueFieldValueId(issue.getId(), field.getId());
        IssueFieldValue value = new IssueFieldValue(id, issue, field, "5d");
        service.save(value);

        service.delete(id);
        assertFalse(issueFieldValueRepository.findById(id).isPresent());
    }

    @Test
    void testFindAll() {
        IssueFieldValueId id = new IssueFieldValueId(issue.getId(), field.getId());
        IssueFieldValue value = new IssueFieldValue(id, issue, field, "5d");
        service.save(value);

        List<IssueFieldValue> all = service.getAll();
        assertEquals(1, all.size());
    }

    @Test
    void testSaveNullThrows() {
        assertThrows(IllegalArgumentException.class, () -> service.save(null));
    }
}
