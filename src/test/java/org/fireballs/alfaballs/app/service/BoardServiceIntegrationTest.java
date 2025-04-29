package org.fireballs.alfaballs.app.service;

import org.fireballs.alfaballs.app.repository.BoardRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Board;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardServiceIntegrationTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BoardRepository boardRepository;

    private Project project;

    @BeforeAll
    void init() {
        project = Project.builder()
                .name("Integration Project")
                .code("IP")
                .build();
        project = projectService.saveProject(project); // если у тебя есть метод save
    }

    @Test
    void saveNewBoard_shouldPersistBoardInDatabase() {
        Board board = Board.builder()
                .name("Board A")
                .build();

        Board saved = boardService.saveNewBoard(project.getId(), board);

        assertNotNull(saved.getId());
        assertEquals("Board A", saved.getName());

        Optional<Board> fromDb = boardRepository.findById(saved.getId());
        assertTrue(fromDb.isPresent());
        assertEquals(project.getId(), fromDb.get().getProject().getId());
    }

    @Test
    void getBoardById_shouldReturnPersistedBoard() {
        Board board = Board.builder()
                .name("Board B")
                .project(project)
                .build();

        board = boardRepository.save(board);

        Board fetched = boardService.getBoardById(board.getId());
        assertEquals(board.getId(), fetched.getId());
    }

    @Test
    void deleteBoard_shouldRemoveBoardFromDatabase() {
        Board board = boardRepository.save(Board.builder()
                .name("Board C")
                .project(project)
                .build());

        boardService.deleteBoard(board.getId());

        assertFalse(boardRepository.findById(board.getId()).isPresent());
    }
}

