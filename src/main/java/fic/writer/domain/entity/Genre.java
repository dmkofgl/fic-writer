package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private Set<Book> book;
}
