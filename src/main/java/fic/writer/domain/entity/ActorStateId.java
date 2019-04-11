package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ActorStateId implements Serializable {
    @Column(name = "article_id")
    private Long articleId;
    @Column(name = "actor_id")
    private Long actorId;

}