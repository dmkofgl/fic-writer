package fic.writer.domain.service;

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
    @Autowired
    ActorStateService actorStateService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ActorService actorService;


    @Test
    public void findActorStateByArticle_whenCorrect_shouldExist() {
        assertTrue(actorStateService.findForActorByArticle(1L, 1L).isPresent());
    }

    @Test
    public void findActorStateForActor_whenCorrect_shouldExist() {
        Pageable pageable = new PageRequest(0, 10);
        assertEquals(2L, actorStateService.findAllByActor(1L, pageable).getTotalElements());
    }
}
