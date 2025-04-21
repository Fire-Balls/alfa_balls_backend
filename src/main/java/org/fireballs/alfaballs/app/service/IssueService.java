package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.IssueRepository;
import org.fireballs.alfaballs.domain.Issue;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository IssueRepository;

    public Issue saveIssue(Issue issue) {
        if (issue == null) {
            throw new IllegalArgumentException("Issue is null");
        }

        IssueRepository.save(issue);
        log.info("Issue {} was created", issue.getId());

        return issue;
    }

    public Issue getIssueById(long issueId) {
        var searchedGame = IssueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue with id " + issueId + " not found"));
        log.info("Issue {} was found", issueId);
        return searchedGame;
    }

    public void deleteIssue(long issueId) {
        IssueRepository.deleteById(issueId);
        log.info("Issue {} was deleted", issueId);
    }

    public List<Issue> getAllIssues() {
        log.info("Get all Issues");
        return IssueRepository.findAll();
    }
}
