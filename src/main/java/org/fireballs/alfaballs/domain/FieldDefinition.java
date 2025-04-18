package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "field_definitions", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class FieldDefinition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    //todo таблицу БД field_data_type
    @Column(name = "data_type", length = 10, nullable = false)
    private String dataType;
}
