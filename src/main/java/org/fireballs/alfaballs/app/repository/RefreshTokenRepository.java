package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshTokenAndExpiresAtAfter(String token, Instant date);
    void deleteByRefreshToken(String token);
}
