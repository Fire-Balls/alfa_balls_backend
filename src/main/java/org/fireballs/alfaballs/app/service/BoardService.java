package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.exception.NotFoundException;
import org.fireballs.alfaballs.app.repository.BoardRepository;
import org.fireballs.alfaballs.app.repository.StatusRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Status;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final ProjectService projectService;
    private final StatusRepository statusRepository;

    public Board saveBoard(long projectId, Board board) {
        Project project = projectService.getProjectById(projectId);
        // проще брать project из борда?
        board.setProject(project);

        Board savedBoard = boardRepository.save(board);
        log.info("New board {} was created in project {}", savedBoard.getId(), project.getId());

        if (savedBoard.getStatuses().isEmpty()) {
            savedBoard.getStatuses().add(new Status(null, "TODO", savedBoard, true));
            savedBoard.getStatuses().add(new Status(null, "DOING", savedBoard, true));
            savedBoard.getStatuses().add(new Status(null, "DONE", savedBoard, true));
        }

        return savedBoard;
    }

    public Board updateBoard(long existingBoardId, Board board) {
        if (board == null || board.getProject() == null) {
            throw new NotFoundException("Board or Project is null");
        }

        Board existingBoard = getBoardById(existingBoardId);

        existingBoard.setName(board.getName());

        return saveBoard(existingBoard.getProject().getId(), existingBoard);
    }

    public Board getBoardById(long boardId) {
        var searchedGame = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException("Board with id " + boardId + " not found"));
        log.info("Board {} was found", boardId);
        return searchedGame;
    }

    public void deleteBoard(long boardId) {
        boardRepository.deleteById(boardId);
        log.info("Board {} was deleted", boardId);
    }
}
