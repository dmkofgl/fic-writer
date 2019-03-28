package fic.writer.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
public class Actor {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private List<Book> books;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<ActorState> actorStates;

}
