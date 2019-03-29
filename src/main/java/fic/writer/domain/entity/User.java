package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String about;
    private String information;
    @ManyToMany(mappedBy = "subAuthors", fetch = FetchType.LAZY)
    private List<Book> booksAsSubAuthor;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Book> booksAsAuthor;


}
