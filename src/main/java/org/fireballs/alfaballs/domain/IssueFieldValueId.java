package org.fireballs.alfaballs.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class IssueFieldValueId implements Serializable {
    private Long issue;
    private Long field;
}
