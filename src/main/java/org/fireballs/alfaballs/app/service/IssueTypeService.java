package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.IssueTypeRepository;
import org.fireballs.alfaballs.domain.IssueType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueTypeService {
    private final IssueTypeRepository IssueTypeRepository;

    public IssueType saveIssueType(IssueType issueType) {
        if (issueType == null) {
            throw new IllegalArgumentException("IssueType is null");
        }

        IssueTypeRepository.save(issueType);
        log.info("IssueType {} was created", issueType.getId());

        return issueType;
    }

    public IssueType getIssueTypeById(long issueTypeId) {
        var searchedGame = IssueTypeRepository.findById(issueTypeId)
                .orElseThrow(() -> new IllegalArgumentException("IssueType with id " + issueTypeId + " not found"));
        log.info("IssueType {} was found", issueTypeId);
        return searchedGame;
    }

    public void deleteIssueType(long issueTypeId) {
        IssueTypeRepository.deleteById(issueTypeId);
        log.info("IssueType {} was deleted", issueTypeId);
    }

    public List<IssueType> getAllIssueTypes() {
        log.info("Get all IssueTypes");
        return IssueTypeRepository.findAll();
    }
}
