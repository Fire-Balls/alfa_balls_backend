package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;
}
