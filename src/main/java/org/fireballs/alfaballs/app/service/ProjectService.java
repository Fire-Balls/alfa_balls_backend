package org.fireballs.alfaballs.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.exception.NotFoundException;
import org.fireballs.alfaballs.app.repository.InviteRepository;
import org.fireballs.alfaballs.app.repository.MembershipRepository;
import org.fireballs.alfaballs.app.repository.ProjectRepository;
import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Membership;
import org.fireballs.alfaballs.domain.ProjectInvite;
import org.fireballs.alfaballs.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ProjectService {
    @Value("${url}")
    private String url;

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final MembershipRepository membershipRepository;
    private final EmailService emailService;
    private final InviteRepository inviteRepository;


    public Project saveProject(Project project) {
        if (project == null) {
            throw new IllegalArgumentException("Project is null");
        }

        Project savedProject = projectRepository.save(project);
        log.info("Project {} was saved", project.getId());

        return savedProject;
    }

    public Project updateProject(long existingProjectId, Project newProject) {
        if (newProject == null) {
            throw new NotFoundException("New project is null");
        }

        Project existingProject = getProjectById(existingProjectId);

        existingProject.setName(newProject.getName());
        //todo issuecode probably doesnt change
        existingProject.setCode(newProject.getCode());

        return saveProject(existingProject);
    }

    public Project getProjectById(long projectId) {
        var searchedGame = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project with id " + projectId + " not found"));
        log.info("Project {} was found", projectId);
        return searchedGame;
    }

    public void deleteProject(long projectId) {
        Project project = getProjectById(projectId);

        // Удаляем проект из всех пользователей
        for (User user : project.getUsers()) {
            user.getProjects().remove(project);
        }
        project.getUsers().clear(); // тоже обнуляем с его стороны

        projectRepository.delete(project);
        log.info("Project {} was deleted", projectId);
    }

    public List<Project> getAllProjects() {
        log.info("Get all projects");
        return projectRepository.findAll();
    }

    public void addUserToProject(Long projectId, Long userId, String role) {
        boolean alreadyExists = membershipRepository.existsByUserIdAndProjectId(userId, projectId);
        if (alreadyExists) {
            log.warn("User {} is already in project {}", userId, projectId);
            return;
        }

        Membership membership = Membership.builder()
                .user(userService.getUserById(userId))
                .project(getProjectById(projectId))
                .role(Membership.ProjectRole.valueOf(role))
                .build();

        membershipRepository.save(membership);
        log.info("User {} added to project {} with role {}", userId, projectId, role);
    }

    public void removeUserFromProject(Long projectId, Long userId) {
        Membership membership = membershipRepository.findByUserIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new RuntimeException("User is not in project"));

        membershipRepository.delete(membership);
        log.info("User {} removed from project {}", userId, projectId);
    }

    public void sendInvite(Long projectId, Long userId, String role) {
        Project project = projectRepository.findById(projectId).orElseThrow();
        User user = userService.getUserById(userId);

        String token = UUID.randomUUID().toString();

        ProjectInvite invite = ProjectInvite.builder()
                .user(user)
                .token(token)
                .accepted(false)
                .project(project)
                .role(Membership.ProjectRole.valueOf(role))
                .expiresAt(LocalDateTime.now().plusDays(3))
                .build();

        inviteRepository.save(invite);

        String link = url + "projects/invite/accept?token=" +  token;
        emailService.sendInviteEmail(user.getEmail(), link);
    }

    public void acceptInvite(String token) {
        ProjectInvite invite = inviteRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException("Token not found"));

        if (invite.isAccepted() || invite.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new NotFoundException("Invite is already accepted");
        }

        Project project = getProjectById(invite.getProject().getId());
        User user = userService.getUserById(invite.getUser().getId());

        addUserToProject(project.getId(), user.getId(), invite.getRole().toString());
    }
}
