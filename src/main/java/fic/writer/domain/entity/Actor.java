package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private Set<Book> books;
    @OneToMany(cascade =
            {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
                    CascadeType.REMOVE
            },
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "actor")
    private Set<ActorState> actorStates;
}
