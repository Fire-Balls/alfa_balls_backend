package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.StatusRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Status;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final BoardService boardService;

    public Status saveNewStatus(long boardId, Status status) {
        Board board = boardService.getBoardById(boardId);

        Status newStatus = Status.builder()
                .name(status.getName())
                .board(board)
                .build();

        Status savedStatus = statusRepository.save(newStatus);
        log.info("New type {} was created in project {}", savedStatus.getId(), board.getId());

        return savedStatus;
    }

    public Status saveExistingStatus(Status status) {
        if (status == null || status.getBoard() == null) {
            throw new IllegalArgumentException("Status or Board is null");
        }

        statusRepository.save(status);
        log.info("Status {} was saved", status.getId());

        return status;
    }

    public Status getStatusById(long statusId) {
        var searchedGame = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status with id " + statusId + " not found"));
        log.info("Status {} was found", statusId);
        return searchedGame;
    }

    public void deleteStatus(long statusId) {
        statusRepository.deleteById(statusId);
        log.info("Status {} was deleted", statusId);
    }
}
