package org.fireballs.alfaballs.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@Getter
public class IssueFieldValueId implements Serializable {
    private Long issue;
    private Long field;
}
