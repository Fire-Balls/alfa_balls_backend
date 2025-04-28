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

        Type newType = Type.builder()
                .name(type.getName())
                .project(project)
                .build();

        Type savedType = typeRepository.save(newType);
        log.info("New type {} was created in project {}", savedType.getId(), project.getId());

        return savedType;
    }

    public Type saveExistingType(Type type) {
        if (type == null || type.getProject() == null) {
            throw new IllegalArgumentException("Type or Project is null");
        }

        typeRepository.save(type);
        log.info("IssueType {} was saved", type.getId());

        return type;
    }

    public Type getTypeById(long issueTypeId) {
        var searchedGame = typeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IllegalArgumentException("IssueType with id " + issueTypeId + " not found"));
        log.info("IssueType {} was found", issueTypeId);
        return searchedGame;
    }

    public void deleteType(long issueTypeId) {
        typeRepository.deleteById(issueTypeId);
        log.info("IssueType {} was deleted", issueTypeId);
    }
}
