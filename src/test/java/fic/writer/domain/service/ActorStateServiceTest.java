package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ActorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActorStateServiceTest {
    @Autowired
    ActorStateService actorStateService;
    @Autowired
    ActorService actorService;

    @Test
    public void createActorState_whenCorrect_shouldFindWithId() {
        final Long ARTICLE_ID = 3L;
        final Long ACTOR_ID = 3L;
        Actor actor = actorService.findById(ACTOR_ID).get();
        ActorState actorState = buildActorState(actor, ARTICLE_ID);
        actor.getActorStates().add(actorState);

        actor = actorService.update(ACTOR_ID, ActorDto.of(actor));
        assertTrue(actorStateService.findById(actorState.getId()).isPresent());
    }

    private ActorState buildActorState(Actor actor, Long articleId) {
        Article article = Article.builder().id(articleId).build();
        ActorStateId actorStateId = ActorStateId.builder().articleId(articleId).actorId(actor.getId()).build();
        return ActorState.builder()
                .id(actorStateId)
                .actor(actor)
                .article(article)
                .build();
    }

    private ActorState buildActorState(Long actorId, Long articleId) {
        Article article = Article.builder().id(articleId).build();
        Actor actor = actorService.findById(actorId).get();
        ActorStateId actorStateId = ActorStateId.builder().articleId(articleId).actorId(actor.getId()).build();
        return ActorState.builder()
                .id(actorStateId)
                .actor(actor)
                .article(article)
                .build();
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
    public void createActorState_whenAlreadyExist_shouldCreateId() {
        final Long ARTICLE_ID = 1L;
        final Long ACTOR_ID = 1L;

        Actor actor = actorService.findById(ACTOR_ID).get();
        ActorState actorState = buildActorState(ACTOR_ID, ARTICLE_ID);
        actor.getActorStates().add(actorState);
        actorService.update(ACTOR_ID, ActorDto.of(actor));

        assertNotNull(actorStateService.findForActorByArticle(ACTOR_ID, ARTICLE_ID));
    }

    @Test
    public void deleteActorState_whenCorrect_shouldNotFoundAfterDelete() {
        final Long ARTICLE_ID = 2L;
        final Long ACTOR_ID = 2L;
        ActorStateId actorStateId = ActorStateId.builder().actorId(ACTOR_ID).articleId(ARTICLE_ID).build();

        Optional<ActorState> actorState = actorStateService.findById(actorStateId);
        assertTrue(actorState.isPresent());

        actorStateService.delete(actorState.get());

        assertFalse(actorStateService.findById(actorStateId).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteActorState_whenIdNotExist_should() {
        final Long ARTICLE_ID = 2L;
        final Long ACTOR_ID = -2L;

        ActorStateId actorStateId = ActorStateId.builder()
                .actorId(ACTOR_ID)
                .articleId(ARTICLE_ID)
                .build();

        actorStateService.deleteById(actorStateId);
    }
}