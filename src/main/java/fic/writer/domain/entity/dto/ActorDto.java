package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class ActorDto {
    private String name;
    private String description;
    private Set<ActorState> actorStates;

    public static ActorDto of(Actor actor) {
        return builder()
                .name(actor.getName())
                .description(actor.getDescription())
                .actorStates(actor.getActorStates())
                .build();
    }
}