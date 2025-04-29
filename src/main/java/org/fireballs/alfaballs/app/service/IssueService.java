package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.IssueRepository;
import org.fireballs.alfaballs.domain.Board;
import org.fireballs.alfaballs.domain.Issue;
import org.fireballs.alfaballs.domain.User;
import org.springframework.stereotype.Service;

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

    public Issue saveIssue(long boardId, Issue issue) {
        Board board = boardService.getBoardById(boardId);
        String code = board.getProject().getCode();
        int issueCount = board.getProject().incrementAndGetLastIssueNumber();

        issue.setBoard(board);
        issue.setCode(code + "-" + issueCount);

        if (issue.getType() == null || !board.getProject().getTypes().contains(issue.getType())) {
            issue.setType(board.getProject().getTypes().stream()
                    .filter(type -> type.isDefault() && type.getName().equals("Feature"))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new));
        }

        if (issue.getStatus() == null || !board.getStatuses().contains(issue.getStatus())) {
            issue.setStatus(board.getStatuses().stream()
                    .filter(status -> status.isDefault() && status.getName().equals("TODO"))
                    .findFirst()
                    .orElseThrow(IllegalArgumentException::new));
        }

        Issue savedIssue = issueRepository.save(issue);
        log.info("New issue {} was created in board {}", savedIssue.getId(), board.getId());

        return savedIssue;
    }

    public Issue updateIssue(long existingIssueId, Issue issue) {
        if (issue == null || issue.getBoard() == null) {
            throw new IllegalArgumentException("Issue or board is null");
        }

        if (issue.getDeadline() == null || issue.getDeadline().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Illegal new deadline");
        }

        Issue existingIssue = getIssueById(existingIssueId);

        existingIssue.setTitle(issue.getTitle());
        existingIssue.setDescription(issue.getDescription());
        existingIssue.setAssignee(issue.getAssignee());
        existingIssue.setStatus(issue.getStatus());
        existingIssue.setType(issue.getType());
        existingIssue.setTags(issue.getTags());
        existingIssue.setDeadline(issue.getDeadline());

        return saveIssue(issue.getBoard().getId(), existingIssue);
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

    public void addObserverToIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.getObservers().add(user);
        saveIssue(issue.getBoard().getId(), issue);
    }

    public void removeObserverFromIssue(long issueId, long userId) {
        Issue issue = getIssueById(issueId);
        User user = userService.getUserById(userId);

        issue.getObservers().remove(user);
        saveIssue(issue.getBoard().getId(), issue);
    }

    public void addDependency(long issueId, long otherIssueId) {
        Issue issue = getIssueById(issueId);
        Issue otherIssue = getIssueById(otherIssueId);

        issue.getDependencies().add(otherIssue);
        saveIssue(issue.getBoard().getId(), issue);
    }

    public void removeDependency(long issueId, long otherIssueId) {
        Issue issue = getIssueById(issueId);
        Issue otherIssue = getIssueById(otherIssueId);

        issue.getDependencies().remove(otherIssue);
        saveIssue(issue.getBoard().getId(), issue);
    }
}
