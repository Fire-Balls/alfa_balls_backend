package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.ProjectInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InviteRepository extends JpaRepository<ProjectInvite, Long> {
    Optional<ProjectInvite> findByToken(String token);
}
