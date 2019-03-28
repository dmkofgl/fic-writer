package fic.writer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Genre {
    @Id
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "genres")
    private List<Book> book;
}
