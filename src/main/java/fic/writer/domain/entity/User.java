package fic.writer.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String about;
    private String information;
    @ManyToMany(mappedBy = "subAuthors")
    private List<Book> books;
    @OneToMany
    private List<Book> writedBook;


}
