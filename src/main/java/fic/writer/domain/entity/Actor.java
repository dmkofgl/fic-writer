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
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private Set<Book> books;
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<ActorState> actorStates;

}
