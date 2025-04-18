package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "issues")
@Inheritance(strategy = InheritanceType.JOINED)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private IssueType type;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToMany
    @JoinTable(name = "issue_observers",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> observers = new HashSet<>();

    @OneToMany(mappedBy = "issue")
    private Set<IssueFieldValue> fieldValues;

    @ManyToMany
    @JoinTable(name = "issue_dependencies",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "depends_on_issue_id"))
    private Set<Issue> dependsOn;

    @ManyToMany(mappedBy = "dependsOn")
    private Set<Issue> dependencies;

    @ManyToOne(optional = false)
    @JoinColumn(name = "epic_id", nullable = false)
    private Epic epic;
}
