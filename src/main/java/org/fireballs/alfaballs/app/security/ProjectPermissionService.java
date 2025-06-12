package org.fireballs.alfaballs.app.security;

import lombok.RequiredArgsConstructor;
import org.fireballs.alfaballs.app.repository.MembershipRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectPermissionService {
    private final MembershipRepository membershipRepository;

    public boolean isOwner(Long projectId, String userEmail) {
        return membershipRepository.findByUserEmailAndProjectId(userEmail, projectId)
                .map(role -> role.getRole().toString().equals("OWNER"))
                .orElse(false);
    }
}

