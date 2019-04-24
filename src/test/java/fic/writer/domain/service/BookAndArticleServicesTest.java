package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@WithMockUser(username = "Bella")
public class BookAndArticleServicesTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BookService bookService;

    @Before
    public void setUserInSecurityContext() {
        CustomUserDetails customUserDetails = new CustomUserDetails(User.builder().id(1L).build(), "qwerty");
        TestingAuthenticationToken token = new TestingAuthenticationToken(customUserDetails, null);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
    @Test
    public void createArticle_shouldFindByGeneratedId() {
        final Long BOOK_ID = 1L;
        Article article = Article.builder().build();
        bookService.addArticle(BOOK_ID, ArticleDto.of(article));

        Book book = bookService.findById(BOOK_ID).get();
        book.getArticles().forEach(art -> assertTrue(articleService.findById(art.getId()).isPresent()));
    }

    @Test
    public void createArticle_shouldGenerateCreatedDate() {
        final Long BOOK_ID = 1L;
        Article article = Article.builder().build();
        bookService.addArticle(BOOK_ID, ArticleDto.of(article));

        Book book = bookService.findById(BOOK_ID).get();
        book.getArticles().forEach(art -> assertNotNull(art.getCreated()));
    }

    @Test
    public void removeArticle_shouldGenerateCreatedDate() {
        final Long BOOK_ID = 334L;
        Book book = bookService.findById(BOOK_ID).get();

        Long articleId = book.getArticles().stream().findFirst().get().getId();
        book = bookService.removeArticle(BOOK_ID, articleId);

        book.getArticles().forEach(art -> assertNotEquals(articleId, art.getId()));
    }
}
