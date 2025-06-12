package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.Project;
import org.fireballs.alfaballs.domain.Membership;
import org.fireballs.alfaballs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByUserAndProject(User user, Project project);

    Optional<Membership> findByUserAndProject(User user, Project project);
}
