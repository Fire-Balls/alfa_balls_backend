package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepository extends JpaRepository<Epic, Long> {
}
