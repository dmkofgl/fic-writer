package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.ActorStateId;
import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.dto.ActorDto;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActorAndActorStateServicesTest {
    @Autowired
    private ActorService actorService;
    @Autowired
    private ActorStateService actorStateService;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void updateActorWithActorStates_whenCorrect_shouldUpdateInActorState() {
        final Long ACTOR_ID = 333L;
        final Long ARTICLE_ID = 3L;
        final String NEW_ACTOR_NAME = "new name";
        Actor actor = actorService.findById(ACTOR_ID).get();
        ActorStateId actorStateId = ActorStateId.builder()
                .actorId(ACTOR_ID)
                .articleId(ARTICLE_ID)
                .build();
        actor.setName(NEW_ACTOR_NAME);
        actorService.update(ACTOR_ID, ActorDto.of(actor));

        ActorState actorState = actorStateService.findById(actorStateId).get();

        assertEquals(NEW_ACTOR_NAME, actorState.getActor().getName());
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Test
    public void deleteActorState_whenCorrect_shouldCleanInActor() {
        final Long ACTOR_ID = 333L;
        final Long ARTICLE_ID = 3L;
        Session session = getSession();
        ActorStateId actorStateId = ActorStateId.builder()
                .actorId(ACTOR_ID)
                .articleId(ARTICLE_ID)
                .build();

        actorStateService.deleteById(actorStateId);
        session.flush();
        Actor actor = actorService.findById(ACTOR_ID).get();

        Boolean isStateInActor = actor.getActorStates().stream().anyMatch(as -> as.getId().equals(actorStateId));
        assertFalse(isStateInActor);
    }


    @Test
    public void deleteActor_whenCorrect_shouldCleanActorStates() {
        final Long ACTOR_ID = 334L;

        Actor actor = actorService.findById(ACTOR_ID).get();

        actorService.deleteById(ACTOR_ID);

        assertFalse(actorStateService.findAll().stream().anyMatch(as -> as.getActor().equals(actor)));
    }

    @Test
    public void createActorState_whenCorrect_shouldCascadeAddInActor() {
        final Long ACTOR_ID = 333L;
        final Long ARTICLE_ID = 1L;
        final int EXPECTED_SIZE = 2;
        Article article = Article.builder().id(ARTICLE_ID).build();
        Actor actor = actorService.findById(ACTOR_ID).get();
        ActorState actorState = ActorState.builder()
                .id(ActorStateId.builder().actorId(ACTOR_ID).articleId(ARTICLE_ID).build())
                .actor(actor)
                .article(article)
                .title("title")
                .build();
        Set<ActorState> actorStates = actor.getActorStates();
        actorStates.add(actorState);
        actor.setActorStates(actorStates);
        actorService.update(ACTOR_ID, ActorDto.of(actor));
        int changedSize = actorService.findById(ACTOR_ID).get().getActorStates().size();
        assertEquals(EXPECTED_SIZE, changedSize);
    }
}
