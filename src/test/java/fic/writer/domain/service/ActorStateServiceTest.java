package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import fic.writer.domain.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorStateServiceTest {
    @Autowired
    ActorStateService actorStateService;

    @Test
    public void createActorState_whenCorrect_shouldFindWithId() {
        ActorStateId actorStateId = ActorStateId.builder()
                .article(Article.builder().id(1L).build())
                .actor(Actor.builder().id(1L).build())
                .build();
        ActorState actorState = ActorState.builder()
                .id(actorStateId)
                .build();
        actorStateService.save(actorState);
        assertTrue(actorStateService.findById(actorStateId).isPresent());
    }

    @Test
    public void findActorStateByArticle_whenCorrect_shouldExist() {
        assertTrue(actorStateService.findForActorByArticle(1L, 1L).isPresent());
    }

    @Test
    public void findActorStateByActor_whenCorrect_shouldExist() {
        Pageable pageable = new PageRequest(0, 10);
        assertNotEquals(0, actorStateService.findAllByActor(1L, pageable).getTotalElements());
    }

    @Test
    public void findActorStateByActor_whenActorNotExist_shouldReturnEmptyPage() {
        Pageable pageable = new PageRequest(0, 10);
        assertEquals(0, actorStateService.findAllByActor(-1L, pageable).getTotalElements());
    }

    @Test
    public void findActorStateByArticle_whenArticleNotExists_shouldExist() {
        assertFalse(actorStateService.findForActorByArticle(1L, -1L).isPresent());
    }

    @Test
    public void createActorState_whenCorrect_shouldCreateId() {
        Actor actor = Actor.builder().id(1L).build();
        Article article = Article.builder().id(1L).build();
        ActorStateId actorStateId = ActorStateId.builder().actor(actor).article(article).build();
        ActorState actorState = ActorState.builder()
                .content("content")
                .id(actorStateId)
                .title("title").build();
        actorStateService.save(actorState);

        assertNotNull(actorState.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createActorState_whenActorNotExist_shouldThrowException() {
        Actor actor = Actor.builder().id(-1L).build();
        Article article = Article.builder().id(1L).build();
        ActorStateId actorStateId = ActorStateId.builder().actor(actor).article(article).build();

        ActorState actorState = ActorState.builder()
                .content("content")
                .id(actorStateId)
                .title("title").build();
        actorStateService.save(actorState);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createActorState_whenArticleNotExist_shouldThrowException() {
        Actor actor = Actor.builder().id(1L).build();
        Article article = Article.builder().id(-1L).build();
        ActorStateId actorStateId = ActorStateId.builder().actor(actor).article(article).build();

        ActorState actorState = ActorState.builder()
                .content("content")
                .id(actorStateId)
                .title("title").build();
        actorStateService.save(actorState);
    }
}