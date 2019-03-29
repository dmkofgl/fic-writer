package fic.writer.domain.entity;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActorState {
    @EmbeddedId
    private ActorStateId id;
    private String title;
    private String content;
}
