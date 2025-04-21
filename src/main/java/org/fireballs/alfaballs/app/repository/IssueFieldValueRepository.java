package org.fireballs.alfaballs.app.repository;

import org.fireballs.alfaballs.domain.IssueFieldValue;
import org.fireballs.alfaballs.domain.IssueFieldValueId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueFieldValueRepository extends JpaRepository<IssueFieldValue, IssueFieldValueId> {
}
