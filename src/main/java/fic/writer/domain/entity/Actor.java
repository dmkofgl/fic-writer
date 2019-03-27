package fic.writer.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Actor {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    @ManyToMany
    private List<Book> books;

}
