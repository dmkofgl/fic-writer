package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
public class ActorState {
    @EmbeddedId
    private ActorStateId id;
    @MapsId("articleId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    @MapsId("actorId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Actor actor;
    private String title;
    private String content;

}
