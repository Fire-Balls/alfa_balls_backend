package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.RoleRepository;
import org.fireballs.alfaballs.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository RoleRepository;

    public Role saveRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role is null");
        }

        RoleRepository.save(role);
        log.info("Role {} was created", role.getId());

        return role;
    }

    public Role getRoleById(long roleId) {
        var searchedGame = RoleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with id " + roleId + " not found"));
        log.info("Role {} was found", roleId);
        return searchedGame;
    }

    public void deleteRole(long roleId) {
        RoleRepository.deleteById(roleId);
        log.info("Role {} was deleted", roleId);
    }

    public List<Role> getAllRoles() {
        log.info("Get all Roles");
        return RoleRepository.findAll();
    }
}
