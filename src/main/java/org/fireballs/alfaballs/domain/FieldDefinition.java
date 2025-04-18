package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "field_definitions", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class FieldDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "data_type", length = 10, nullable = false)
    private String dataType;
}
