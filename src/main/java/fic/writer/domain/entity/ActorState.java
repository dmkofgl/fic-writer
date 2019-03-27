package fic.writer.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ActorState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Article article;
    @ManyToOne
    private Actor actor;
    private String title;
    private String content;

}
