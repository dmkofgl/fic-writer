package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJpaAuditing
public class BookAndArticleServicesTest {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private BookService bookService;

    @Test
    public void createArticle_shouldFindByGeneratedId() {
        final Long BOOK_ID = 1L;
        Book book = bookService.findById(BOOK_ID).get();
        Article article = Article.builder().build();
        book.getArticles().add(article);
        book = bookService.save(book);

        book.getArticles().forEach(art -> assertTrue(articleService.findById(art.getId()).isPresent()));
    }

    @Test
    public void createArticle_shouldGenerateCreatedDate() {
        final Long BOOK_ID = 1L;
        Book book = bookService.findById(BOOK_ID).get();
        Article article = Article.builder().build();
        book.getArticles().add(article);
        book = bookService.save(book);

        book.getArticles().forEach(art -> assertNotNull(art.getCreated()));

    }
}
