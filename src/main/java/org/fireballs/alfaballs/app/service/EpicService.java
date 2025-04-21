package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.EpicRepository;
import org.fireballs.alfaballs.domain.Epic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EpicService {
    private final EpicRepository EpicRepository;

    public Epic saveEpic(Epic epic) {
        if (epic == null) {
            throw new IllegalArgumentException("Epic is null");
        }

        EpicRepository.save(epic);
        log.info("Epic {} was created", epic.getId());

        return epic;
    }

    public Epic getEpicById(long epicId) {
        var searchedGame = EpicRepository.findById(epicId)
                .orElseThrow(() -> new IllegalArgumentException("Epic with id " + epicId + " not found"));
        log.info("Epic {} was found", epicId);
        return searchedGame;
    }

    public void deleteEpic(long epicId) {
        EpicRepository.deleteById(epicId);
        log.info("Epic {} was deleted", epicId);
    }

    public List<Epic> getAllEpics() {
        log.info("Get all Epics");
        return EpicRepository.findAll();
    }
}
