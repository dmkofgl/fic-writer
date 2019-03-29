package fic.writer.domain.entity;

import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @OneToMany(fetch = FetchType.EAGER)
    private List<Book> source;
    private String description;
    @Enumerated
    private Size size;
    @Enumerated
    private State state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Article> articles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genres",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> genres;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_actors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<Actor> actors;
}
