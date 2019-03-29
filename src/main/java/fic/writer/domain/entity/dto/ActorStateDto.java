package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActorStateDto {
    private ActorStateId id;
    private String title;
    private String content;


    public static ActorStateDto of(ActorState actorState) {
        return builder()
                .id(actorState.getId())
                .title(actorState.getTitle())
                .content(actorState.getContent())
                .build();
    }
}
