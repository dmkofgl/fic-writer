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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String about;
    private String information;
    @ManyToMany(mappedBy = "subAuthors", fetch = FetchType.LAZY)
    private Set<Book> booksAsSubAuthor;
    @OneToMany(fetch = FetchType.LAZY)
    private Set<Book> booksAsAuthor;


}
