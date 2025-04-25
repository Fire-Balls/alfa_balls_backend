package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.BoardRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ProjectService projectService;

    public Board saveNewBoard(long projectId, String boardName) {
        Project project = projectService.getProjectById(projectId);

        Board board = new Board();
        board.setName(boardName);
        board.setProject(project);

        Board savedBoard = boardRepository.save(board);
        log.info("New Board {} was created", board.getId());

        return savedBoard;
    }

    public Board updateBoard(Board board) {
        if (board == null || board.getId() == null) {
            throw new IllegalArgumentException("Project is null");
        }

        Board savedBoard = boardRepository.save(board);
        log.info("Board {} was created", board.getId());

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
