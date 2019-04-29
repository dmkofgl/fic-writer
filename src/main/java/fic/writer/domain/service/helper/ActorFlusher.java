package fic.writer.domain.service.helper;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;

public class ActorFlusher {
    public static void flushArticleDtoToArticle(Actor actor, ActorDto actorDto) {
        if (actorDto.getName() != null) {
            actor.setName(actorDto.getName());
        }
        if (actorDto.getActorStates() != null) {
            actor.setActorStates(actorDto.getActorStates());
        }
        if (actorDto.getDescription() != null) {
            actor.setDescription(actorDto.getDescription());
        }
    }
}
