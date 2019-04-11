package fic.writer.domain.entity;

import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    private User author;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_subauthors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @Singular("subAuthors")
    private Set<User> subAuthors;
    @OneToMany(fetch = FetchType.EAGER)
    @Singular("source")
    private Set<Book> source;
    private String description;
    @Enumerated
    private Size size;
    @Enumerated
    private State state;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Singular("articles")
    private Set<Article> articles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genres",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_actors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    @Singular("actors")
    private Set<Actor> actors;
}
