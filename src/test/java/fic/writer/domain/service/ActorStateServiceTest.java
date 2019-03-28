package fic.writer.domain.service;

import fic.writer.domain.entity.ActorState;
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
    public void createActorState_whenCorrect_shouldReturnGeneratedId() {
        ActorState actorState = ActorState.builder().build();
        actorStateService.save(actorState);
        assertNotNull(actorState.getId());
    }


}