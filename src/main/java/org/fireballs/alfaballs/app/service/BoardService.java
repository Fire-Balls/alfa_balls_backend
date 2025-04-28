package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.BoardRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ProjectService projectService;

    public Board saveNewBoard(long projectId, Board board) {
        Project project = projectService.getProjectById(projectId);

        board.setProject(project);

        Board savedBoard = boardRepository.save(board);
        log.info("New board {} was created in project {}", savedBoard.getId(), project.getId());

        return savedBoard;
    }

    public Board updateBoard(Board board) {
        if (board == null || board.getProject() == null) {
            throw new IllegalArgumentException("Board or Project is null");
        }

        Board savedBoard = boardRepository.save(board);
        log.info("Board {} was saved", board.getId());

        return savedBoard;
    }

    public Board getBoardById(long boardId) {
        var searchedGame = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board with id " + boardId + " not found"));
        log.info("Board {} was found", boardId);
        return searchedGame;
    }

    public void deleteBoard(long boardId) {
        boardRepository.deleteById(boardId);
        log.info("Board {} was deleted", boardId);
    }

    public List<Board> getAllBoards() {
        log.info("Get all Boards");
        return boardRepository.findAll();
    }
}
