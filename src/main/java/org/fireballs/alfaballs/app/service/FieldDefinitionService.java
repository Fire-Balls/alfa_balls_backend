package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.FieldDefinitionRepository;
import org.fireballs.alfaballs.domain.FieldDefinition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FieldDefinitionService {
    private final FieldDefinitionRepository FieldDefinitionRepository;

    public FieldDefinition saveFieldDefinition(FieldDefinition fieldDefinition) {
        if (fieldDefinition == null) {
            throw new IllegalArgumentException("FieldDefinition is null");
        }

        FieldDefinitionRepository.save(fieldDefinition);
        log.info("FieldDefinition {} was created", fieldDefinition.getId());

        return fieldDefinition;
    }

    public FieldDefinition getFieldDefinitionById(long fieldDefinitionId) {
        var searchedGame = FieldDefinitionRepository.findById(fieldDefinitionId)
                .orElseThrow(() -> new IllegalArgumentException("FieldDefinition with id " + fieldDefinitionId + " not found"));
        log.info("FieldDefinition {} was found", fieldDefinitionId);
        return searchedGame;
    }

    public void deleteFieldDefinition(long fieldDefinitionId) {
        FieldDefinitionRepository.deleteById(fieldDefinitionId);
        log.info("FieldDefinition {} was deleted", fieldDefinitionId);
    }

    public List<FieldDefinition> getAllFieldDefinitions() {
        log.info("Get all FieldDefinitions");
        return FieldDefinitionRepository.findAll();
    }
}
