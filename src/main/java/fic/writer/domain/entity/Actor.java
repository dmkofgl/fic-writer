package fic.writer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Actor {
    @Id
    private UUID uuid;
    private String name;
    private String description;
    @ManyToMany
    private List<Book> books;

}
