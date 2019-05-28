package fic.writer.domain.entity;

import fic.writer.domain.entity.enums.Size;
import fic.writer.domain.entity.enums.State;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @CreatedBy
    @NotNull
    private Profile author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_subauthors",
            joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @Singular("coauthors")
    private Set<Profile> coauthors;

    @OneToMany(fetch = FetchType.EAGER)
    @Singular("source")
    private Set<Book> source;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated
    private Size size;

    @Enumerated
    private State state;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Singular("articles")
    private Set<Article> articles;

    @Transient
    private Long pageCount;

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

    @PostLoad
    private void calculatePageCount() {
        this.pageCount = 0L;
        if (articles != null) {
            pageCount = articles.stream().mapToLong(Article::getPageCount).sum();
        }
    }

}
