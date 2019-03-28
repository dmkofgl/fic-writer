package fic.writer.domain.entity.dto;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.Article;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActorStateDto {
    private Long id;
    private Article article;
    private Actor actor;
    private String title;
    private String content;


    public static ActorStateDto of(ActorState actorState) {
        return builder()
                .id(actorState.getId())
                .article(actorState.getArticle())
                .actor(actorState.getActor())
                .title(actorState.getTitle())
                .content(actorState.getContent())
                .build();
    }
}
