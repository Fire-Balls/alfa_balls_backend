package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    boolean existsByUserIdAndProjectId(Long userId, Long projectId);
    Optional<Membership> findByUserEmailAndProjectId(String userEmail, Long projectId);
    Optional<Membership> findByUserIdAndProjectId(Long userId, Long projectId);
}
