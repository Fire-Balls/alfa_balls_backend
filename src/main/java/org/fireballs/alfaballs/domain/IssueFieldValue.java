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
public class IssueFieldValue {
    @EmbeddedId
    private IssueFieldValueId issueFieldValue;

    @MapsId("issue") // название поля в EmbeddedId
    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @MapsId("field") // название поля в EmbeddedId
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    private FieldDefinition field;

    //todo таблицу БД field_data_type
    @Column(nullable = false, columnDefinition = "text")
    private String text;
}
