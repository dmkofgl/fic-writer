package fic.writer.domain.service;

import fic.writer.domain.entity.Actor;
import fic.writer.domain.entity.dto.ActorDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActorServiceTest {
    @Autowired
    private ActorService actorService;

    @Test
    public void createActor_whenCorrect_shouldFindWithId() {
        Actor actor = Actor.builder().build();
        actor = actorService.create(ActorDto.of(actor));

        assertTrue(actorService.findById(actor.getId()).isPresent());
    }

    @Test
    public void createActor_whenIdExists_shouldFindUpdate() {
        final Long ACTOR_ID = 1L;
        final String NEW_NAME = "new name";
        actorService.update(ACTOR_ID, ActorDto.builder().name(NEW_NAME).build());
        Optional<Actor> updatedActor = actorService.findById(ACTOR_ID);
        assertTrue(updatedActor.isPresent());
        assertEquals(NEW_NAME, updatedActor.get().getName());
    }

    @Test
    public void deleteActor_whenExists_shouldNotFound() {
        final Long AUTHOR_ID = 987L;
        assertTrue(actorService.findById(AUTHOR_ID).isPresent());
        actorService.deleteById(AUTHOR_ID);
        assertFalse(actorService.findById(AUTHOR_ID).isPresent());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteActor_whenNotExists_shouldThrowException() {
        final Long AUTHOR_ID = -1L;

        assertFalse(actorService.findById(AUTHOR_ID).isPresent());
        actorService.deleteById(AUTHOR_ID);
    }


}