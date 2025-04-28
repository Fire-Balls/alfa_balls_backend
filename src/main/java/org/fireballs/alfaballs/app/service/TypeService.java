package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.TypeRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Type;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;
    private final ProjectService projectService;

    public Type saveNewType(long projectId, Type type) {
        Project project = projectService.getProjectById(projectId);

        type.setProject(project);

        Type savedType = typeRepository.save(type);
        log.info("New type {} was created in project {}", savedType.getId(), project.getId());

        return savedType;
    }

    public Type updateType(long existingTypeId, Type newType) {
        if (newType == null || newType.getProject() == null) {
            throw new IllegalArgumentException("Type or Project is null");
        }

        Type existingType = getTypeById(existingTypeId);

        existingType.setName(newType.getName());

        return saveNewType(existingType.getProject().getId(), existingType);
    }

    public Type getTypeById(long typeId) {
        var searchedGame = typeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Type with id " + typeId + " not found"));
        log.info("Type {} was found", typeId);
        return searchedGame;
    }

    public void deleteType(long typeId) {
        typeRepository.deleteById(typeId);
        log.info("Type {} was deleted", typeId);
    }
}
