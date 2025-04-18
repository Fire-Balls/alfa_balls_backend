package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issue_field_values")
@IdClass(IssueFieldValueId.class)
public class IssueFieldValue {
    @Id
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Id
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldDefinition field;

    //todo таблицу БД field_data_type
    @Column(nullable = false, columnDefinition = "text")
    private String text;
}
