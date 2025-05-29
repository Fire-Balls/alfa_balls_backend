//package org.fireballs.alfaballs.app.service;
//
//import org.fireballs.alfaballs.app.repository.BoardRepository;
//import org.fireballs.alfaballs.domain.Board;
//import org.fireballs.alfaballs.domain.Project;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BoardServiceTest {
//
//    @Mock
//    private BoardRepository boardRepository;
//
//    @Mock
//    private ProjectService projectService;
//
//    @InjectMocks
//    private BoardService boardService;
//
//    private Board board;
//    private Project project;
//
//    @BeforeEach
//    void setUp() {
//        project = Project.builder().id(1L).name("Test Project").code("TP").build();
//        board = Board.builder().id(10L).name("Test Board").project(project).build();
//    }
//
//
//    @Test
//    void saveNewBoard_shouldSaveBoardWithProject() {
//        when(projectService.getProjectById(1L)).thenReturn(project);
//        when(boardRepository.save(any(Board.class))).thenAnswer(invocation -> {
//            Board b = invocation.getArgument(0);
//            b.setId(10L);
//            return b;
//        });
//
//        Board boardToSave = Board.builder().name("New Board").build();
//        Board saved = boardService.saveNewBoard(1L, boardToSave);
//
//        assertEquals(10L, saved.getId());
//        assertEquals(project, saved.getProject());
//
//        verify(projectService).getProjectById(1L);
//        verify(boardRepository).save(boardToSave);
//    }
//
//    @Test
//    void updateBoard_shouldSaveIfBoardAndProjectPresent() {
//        when(boardRepository.save(board)).thenReturn(board);
//
//        Board result = boardService.updateBoard(board);
//
//        assertNotNull(result);
//        assertEquals(10L, result.getId());
//        verify(boardRepository).save(board);
//    }
//
//
//    @Test
//    void updateBoard_shouldUpdateValidBoard() {
//        when(boardRepository.save(board)).thenReturn(board);
//
//        Board result = boardService.updateBoard(board);
//
//        assertEquals(board.getId(), result.getId());
//        verify(boardRepository).save(board);
//    }
//
//    @Test
//    void updateBoard_shouldThrowIfBoardIsNull() {
//        assertThrows(IllegalArgumentException.class, () -> boardService.updateBoard(null));
//    }
//
//    @Test
//    void updateBoard_shouldThrowIfProjectIsNull() {
//        Board boardWithoutProject = new Board();
//        assertThrows(IllegalArgumentException.class, () -> boardService.updateBoard(boardWithoutProject));
//    }
//
//    @Test
//    void getBoardById_shouldReturnBoardIfFound() {
//        when(boardRepository.findById(10L)).thenReturn(Optional.of(board));
//
//        Board result = boardService.getBoardById(10L);
//
//        assertEquals(board.getId(), result.getId());
//        verify(boardRepository).findById(10L);
//    }
//
//    @Test
//    void getBoardById_shouldThrowIfNotFound() {
//        when(boardRepository.findById(10L)).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> boardService.getBoardById(10L));
//    }
//
//    @Test
//    void deleteBoard_shouldCallDelete() {
//        boardService.deleteBoard(10L);
//        verify(boardRepository).deleteById(10L);
//    }
//
//    @Test
//    void getAllBoards_shouldReturnAllBoards() {
//        List<Board> boards = List.of(board, new Board());
//        when(boardRepository.findAll()).thenReturn(boards);
//
//        List<Board> result = boardService.getAllBoards();
//
//        assertEquals(2, result.size());
//        verify(boardRepository).findAll();
//    }
//
//    @Test
//    void saveNewBoard_shouldThrowWhenDuplicateName() {
//        when(projectService.getProjectById(1L)).thenReturn(project);
//        when(boardRepository.save(any(Board.class)))
//                .thenThrow(new DataIntegrityViolationException("Duplicate"));
//
//        Board boardToSave = Board.builder().name("Existing").build();
//
//        assertThrows(DataIntegrityViolationException.class, () ->
//                boardService.saveNewBoard(1L, boardToSave)
//        );
//    }
//
//}
