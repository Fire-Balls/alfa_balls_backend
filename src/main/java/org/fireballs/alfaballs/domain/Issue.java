package org.fireballs.alfaballs.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
    private Type type;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "issue_observers",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> observers = new HashSet<>();

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "issue_dependencies",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "depends_on_issue_id"))
    private Set<Issue> dependencies = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User assignee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column
    private LocalDateTime deadline;

    @Column(unique = true, nullable = false)
    private String code;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "issue_tags", joinColumns = @JoinColumn(name = "issue_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "epic_id", nullable = false)
//    private Epic epic;
//
//    @Builder.Default
//    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<IssueFieldValue> fieldValues = new HashSet<>();
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private Priority priority;

//    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<File> files = new ArrayList<>();

//    public enum Priority {
//        HIGH,
//        LOW
//    }
}
