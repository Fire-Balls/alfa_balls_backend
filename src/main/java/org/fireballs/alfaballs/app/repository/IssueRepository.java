package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query("SELECT t FROM Issue t WHERE t.board.project.id = :projectId")
    List<Issue> findAllByProjectId(@Param("projectId") Long projectId);
}
