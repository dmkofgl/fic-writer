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
@Table(name = "profile")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String about;
    private String information;
    @ManyToMany(mappedBy = "subAuthors", fetch = FetchType.LAZY)
    @Singular("booksAsSubAuthor")
    private Set<Book> booksAsSubAuthor;
    @OneToMany(fetch = FetchType.LAZY)
    @Singular("booksAsAuthor")
    private Set<Book> booksAsAuthor;
    private String email;
}
