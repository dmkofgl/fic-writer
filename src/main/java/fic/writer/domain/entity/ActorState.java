package fic.writer.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
public class ActorState {
    @Id
    private UUID uuid;
    @OneToOne
    private Article article;
    @ManyToOne
    private Actor actor;
    private String title;
    private String content;

}
