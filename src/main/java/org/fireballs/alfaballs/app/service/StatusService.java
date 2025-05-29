package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.exception.NotFoundException;
import org.fireballs.alfaballs.app.repository.StatusRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Status;
import org.fireballs.alfaballs.domain.Type;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StatusService {
    private final StatusRepository statusRepository;
    private final BoardService boardService;

    public Status saveNewStatus(long boardId, Status status) {
        Board board = boardService.getBoardById(boardId);

        status.setBoard(board);

        Status savedStatus = statusRepository.save(status);
        log.info("New status {} was created in board {}", savedStatus.getId(), board.getId());

        return savedStatus;
    }

    public Status updateStatus(long existingStatusId, Status newStatus) {
        if (newStatus == null || newStatus.getBoard() == null) {
            throw new NotFoundException("Status or Board is null");
        }

        Status existingStatus = getStatusById(existingStatusId);

        existingStatus.setName(newStatus.getName());

        return saveNewStatus(existingStatus.getBoard().getId(), existingStatus);
    }

    public Status getStatusById(long statusId) {
        var searchedGame = statusRepository.findById(statusId)
                .orElseThrow(() -> new NotFoundException("Status with id " + statusId + " not found"));
        log.info("Status {} was found", statusId);
        return searchedGame;
    }

    public void deleteStatus(long statusId) {
        if (!getStatusById(statusId).isDefault()) {
            statusRepository.deleteById(statusId);
            log.info("Status {} was deleted", statusId);
        } else {
            throw new IllegalArgumentException("Status with id " + statusId + " is default");
        }
    }
}
