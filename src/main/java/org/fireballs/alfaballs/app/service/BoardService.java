package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.BoardRepository;
import org.fireballs.alfaballs.domain.Board;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository BoardRepository;

    public Board saveBoard(Board board) {
        if (board == null) {
            throw new IllegalArgumentException("Board is null");
        }

        BoardRepository.save(board);
        log.info("Board {} was created", board.getId());

        return board;
    }

    public Board getBoardById(long boardId) {
        var searchedGame = BoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("Board with id " + boardId + " not found"));
        log.info("Board {} was found", boardId);
        return searchedGame;
    }

    public void deleteBoard(long boardId) {
        BoardRepository.deleteById(boardId);
        log.info("Board {} was deleted", boardId);
    }

    public List<Board> getAllBoards() {
        log.info("Get all Boards");
        return BoardRepository.findAll();
    }
}
