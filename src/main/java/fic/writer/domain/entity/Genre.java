package fic.writer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Genre {
    @Id
    private Long id;
    private String name;
}
