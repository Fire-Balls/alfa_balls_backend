package org.fireballs.alfaballs.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Entity
@Table(name = "epics")
@NoArgsConstructor
public class Epic extends Issue {

    private String debugString;

//    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Issue> tasks = new ArrayList<>();
}
