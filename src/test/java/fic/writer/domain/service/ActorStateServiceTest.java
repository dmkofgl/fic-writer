package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
import fic.writer.domain.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorStateServiceTest {
    @Autowired
    ActorStateService actorStateService;

    @Test
    public void createActorState_whenCorrect_shouldReturnGeneratedUUID() {
        ActorState actorState = new ActorState();
        actorStateService.save(actorState);
        assertNotNull(actorState.getId());
    }

    @Test
    public void findActorStateByArticle_whenCorrect_shouldFind() {
        ActorState actorState = new ActorState();
        Article article = new Article();

        actorState.setArticle(article);
        actorStateService.save(actorState);
        assertNotNull(actorState.getId());
    }

}