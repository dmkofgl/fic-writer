package fic.writer.domain.entity;

import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Book {
    @Id
    private long id;
    private String title;
    @ManyToOne
    private User author;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_subauthors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> subAuthors;
    @OneToMany
    private List<Book> source;
    private String description;
    private Size size;
    private State state;
    @OneToMany
    private List<Article> articles;
    @ManyToOne
    private Genre genre;
    @ManyToMany
    private List<Actor> actors;
}
