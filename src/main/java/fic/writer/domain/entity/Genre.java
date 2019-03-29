package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Genre {
    @Id
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Book> book;
}
