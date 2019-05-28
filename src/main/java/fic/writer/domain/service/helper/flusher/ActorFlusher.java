package fic.writer.domain.service.helper.flusher;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;

public class ActorFlusher {
    public static void flushActorDtoToArticle(Actor actor, ActorDto actorDto) {
        if (actorDto.getName() != null) {
            actor.setName(actorDto.getName());
        }
        if (actorDto.getDescription() != null) {
            actor.setDescription(actorDto.getDescription());
        }
    }

    public static Actor convertArticleDtoToArticle(ActorDto actorDto) {
        Actor actor = new Actor();
        flushActorDtoToArticle(actor, actorDto);
        return actor;
    }
}
