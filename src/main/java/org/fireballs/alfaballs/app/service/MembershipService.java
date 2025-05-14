package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.MembershipRepository;
import org.fireballs.alfaballs.domain.Membership;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository MembershipRepository;

    public Membership saveRole(Membership membership) {
        if (membership == null) {
            throw new IllegalArgumentException("Role is null");
        }

        MembershipRepository.save(membership);
        log.info("Role {} was created", membership.getId());

        return membership;
    }

    public Membership getRoleById(long roleId) {
        var searchedGame = MembershipRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " not found"));
        log.info("Role {} was found", roleId);
        return searchedGame;
    }

    public void deleteRole(long roleId) {
        MembershipRepository.deleteById(roleId);
        log.info("Role {} was deleted", roleId);
    }

    public List<Membership> getAllRoles() {
        log.info("Get all Roles");
        return MembershipRepository.findAll();
    }
}

