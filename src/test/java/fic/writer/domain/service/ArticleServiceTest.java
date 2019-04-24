package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.User;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.web.config.security.authorization.CustomUserDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Before
    public void setUserInSecurityContext() {
        CustomUserDetails customUserDetails = new CustomUserDetails(User.builder().id(1L).build(), "qwerty");
        TestingAuthenticationToken token = new TestingAuthenticationToken(customUserDetails, null);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Test
    public void updateArticle_whenUpdateTitle_shouldChangeTitle() {
        final Long ARTICLE_ID = 3L;
        final String NEW_TITLE = "new title";

        ArticleDto articleDto = ArticleDto.builder().title(NEW_TITLE).build();
        articleService.update(ARTICLE_ID, articleDto);
        Article article = articleService.findById(ARTICLE_ID).get();
        assertEquals(NEW_TITLE, article.getTitle());
    }

    @Test
    public void updateArticle_whenUpdateTitle_shouldChangeUpdateDate() {
        final Long ARTICLE_ID = 4L;
        final String NEW_TITLE = "new title";
        Date prevUpdateDate = articleService.findById(ARTICLE_ID).get().getLastModify();

        ArticleDto articleDto = ArticleDto.builder().title(NEW_TITLE).build();
        articleService.update(ARTICLE_ID, articleDto);
        Article article = articleService.findById(ARTICLE_ID).get();
        assertNotEquals(prevUpdateDate, article.getLastModify());
    }

    @Test
    public void deleteArticle_whenExist_shouldNotFoundById() {
        final Long ARTICLE_ID = 333L;
        articleService.deleteById(ARTICLE_ID);
        assertFalse(articleService.findById(ARTICLE_ID).isPresent());
    }

}