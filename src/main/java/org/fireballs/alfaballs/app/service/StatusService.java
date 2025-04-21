package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.StatusRepository;
import org.fireballs.alfaballs.domain.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository StatusRepository;

    public Status saveStatus(Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status is null");
        }

        StatusRepository.save(status);
        log.info("Status {} was created", status.getId());

        return status;
    }

    public Status getStatusById(long statusId) {
        var searchedGame = StatusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Status with id " + statusId + " not found"));
        log.info("Status {} was found", statusId);
        return searchedGame;
    }

    public void deleteStatus(long statusId) {
        StatusRepository.deleteById(statusId);
        log.info("Status {} was deleted", statusId);
    }

    public List<Status> getAllStatuss() {
        log.info("Get all Statuss");
        return StatusRepository.findAll();
    }
}
