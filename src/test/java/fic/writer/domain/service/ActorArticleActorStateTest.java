package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorArticleActorStateTest {
    ActorStateService actorStateService;
    ArticleService articleService;
    ActorService actorService;

    @Autowired
    public ActorArticleActorStateTest(ActorStateService actorStateService, ArticleService articleService, ActorService actorService) {
        this.actorStateService = actorStateService;
        this.articleService = articleService;
        this.actorService = actorService;
    }

    @Test
    public void findActorStateByArticle_whenCorrect_shouldExist() {
        Article article = Article.builder()
                .title("title")
                .id(1L)
                .build();
        Actor actor = Actor.builder()
                .name("actor")
                .id(1L)
                .build();
        ActorState actorState = ActorState.builder()
                .article(article)
                .actor(actor)
                .build();
        articleService.save(article);
        actorService.save(actor);
        actorStateService.save(actorState);
        assertTrue(actorStateService.findForActorByArticle(1L, 1L).isPresent());
    }

    @Test
    public void findActorStateForActor_whenCorrect_shouldExist() {
        Actor actor = Actor.builder()
                .name("actor")
                .id(1L)
                .build();
        actorService.save(actor);
        actorStateService.save(ActorState.builder()
                .actor(actor)
                .title("t1")
                .build());
        actorStateService.save(ActorState.builder()
                .actor(Actor.builder()
                        .name("actor")
                        .build())
                .title("t2")
                .build());
        actorStateService.save(ActorState.builder()
                .actor(actor)
                .title("t3")
                .build());

        Pageable pageable = new PageRequest(0, 10);
        assertEquals(2L, actorStateService.findAllByActor(1L, pageable).getTotalElements());
    }
}
