package org.fireballs.alfaballs.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fireballs.alfaballs.app.repository.IssueFieldValueRepository;
import org.fireballs.alfaballs.domain.IssueFieldValue;
import org.fireballs.alfaballs.domain.IssueFieldValueId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueFieldValueService {
    private final IssueFieldValueRepository repository;

    public IssueFieldValue save(IssueFieldValue value) {
        if (value == null) throw new IllegalArgumentException("IssueFieldValue is null");
        repository.save(value);
        log.info("IssueFieldValue was created");
        return value;
    }

    public IssueFieldValue getById(IssueFieldValueId id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("IssueFieldValue with given id not found"));
    }

    public void delete(IssueFieldValueId id) {
        repository.deleteById(id);
        log.info("IssueFieldValue was deleted");
    }

    public List<IssueFieldValue> getAll() {
        return repository.findAll();
    }
}
