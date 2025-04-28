package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.IssueRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final BoardService boardService;
    private final UserService userService;

    public Issue saveNewIssue(long boardId, Issue issue) {
        Board board = boardService.getBoardById(boardId);
        String code = board.getProject().getCode();
        int issueCount = board.getProject().incrementAndGetLastIssueNumber();

        issue.setBoard(board);
        issue.setCode(code + "-" + issueCount);

        Issue savedIssue = issueRepository.save(issue);
        log.info("New issue {} was created in board {}", savedIssue.getId(), board.getId());

        return savedIssue;
    }

    public Issue updateIssue(Issue issue) {
        if (issue == null || issue.getBoard() == null) {
            throw new IllegalArgumentException("Issue or board is null");
        }

        issueRepository.save(issue);
        log.info("Issue {} was saved", issue.getId());

        return issue;
    }

    public Issue getIssueById(long issueId) {
        var searchedGame = issueRepository.findById(issueId)
                .orElseThrow(() -> new IllegalArgumentException("Issue with id " + issueId + " not found"));
        log.info("Issue {} was found", issueId);
        return searchedGame;
    }

    public void deleteIssue(long issueId) {
        issueRepository.deleteById(issueId);
        log.info("Issue {} was deleted", issueId);
    }

    public List<Issue> getAllIssuesByProjectId(long projectId) {
        log.info("Get all Issues by project {}", projectId);
        return issueRepository.findAllByProjectId(projectId);
    }

    public void addAssigneeToIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.setAssignee(user);
        user.getAssignedIssues().add(issue);

        updateIssue(issue);
    }

    public void removeAssigneeFromIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.setAssignee(null);
        user.getAssignedIssues().remove(issue);

        updateIssue(issue);
    }

    public void addObserverToIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.getObservers().add(user);
        user.getObservingIssues().add(issue);

        updateIssue(issue);
    }

    public void removeObserverFromIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.getObservers().remove(user);
        user.getObservingIssues().remove(issue);

        updateIssue(issue);
    }

    public void addTag(long issueId, String tag) {
        Issue issue = getIssueById(issueId);
        issue.getTags().add(tag);
        updateIssue(issue);
    }

    public void removeTag(long issueId, String tag) {
        Issue issue = getIssueById(issueId);
        issue.getTags().remove(tag);
        updateIssue(issue);
    }

    public void modeDeadline(long issueId, LocalDateTime newDeadline) {
        Issue issue = getIssueById(issueId);
        LocalDateTime currentDeadline = issue.getDeadline();
        if (currentDeadline == null || currentDeadline.isBefore(newDeadline)) {
            issue.setDeadline(newDeadline);
            updateIssue(issue);
        } else {
            throw new IllegalArgumentException("Illegal new deadline");
        }
    }


}
