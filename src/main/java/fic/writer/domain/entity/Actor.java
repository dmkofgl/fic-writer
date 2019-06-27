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
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "actors")
    private Set<Book> books;
}