package fic.writer.domain.service;

import fic.writer.domain.entity.Article;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.web.config.security.authorization.UserPrincipal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private ProfileService profileService;

    @Test
    public void createBook_shouldChangeCount() {
        final long profileId = 1L;
        final int SIZE_BEFORE = bookService.findAll().size();
        Book emptyBook = Book.builder().title("title").build();
        setUserInSecurityContext(profileId);
        bookService.create(BookDto.of(emptyBook));

        assertNotEquals(SIZE_BEFORE, bookService.findAll().size());
    }

    @Test
    public void createBook_whenTitleIsCyrillic_shouldFindByGeneratedId() {
        final String TITLE = "Заголовок";
        final long CURRENT_COUNT = 1L;
        final long authorId = 1L;
        Book emptyBook = Book.builder().title(TITLE).build();
        setUserInSecurityContext(authorId);
        bookService.create(BookDto.of(emptyBook));

        assertEquals(CURRENT_COUNT, bookService.findAll().stream().filter(book -> book.getTitle().equals(TITLE)).count());
    }

    private void setUserInSecurityContext(Long profileId) {
        Profile profile = profileService.findById(profileId).get();
        this.setUserInSecurityContext(profile);
    }

    private void setUserInSecurityContext(Profile profile) {
        final String PASSWORD = "qwerty";
        OauthProfileDetails details = OauthProfileDetails.builder().profile(profile).build();
        UserPrincipal profileDetails = UserPrincipal.create(details);
        TestingAuthenticationToken token = new TestingAuthenticationToken(profileDetails, null);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    @Test
    public void findBook_whenContainSizeEnum_shouldConvertToNotNullSize() {
        final Long BOOK_ID = 3L;
        Book book = bookService.findById(BOOK_ID).get();

        assertNotNull(book.getSize());
    }

    @Test
    public void findBook_whenContainStateEnum_shouldConvertToNotNullState() {
        final Long BOOK_ID = 4L;
        Book book = bookService.findById(BOOK_ID).get();

        assertNotNull(book.getState());
    }

    @Test
    public void deletedBook_whenExist_shouldNotFound() {
        final Long DELETE_BOOK_ID = 333L;
        bookService.deleteById(DELETE_BOOK_ID);

        assertNotNull(bookService.findById(DELETE_BOOK_ID));
    }

    @Test(expected = NoSuchElementException.class)
    public void deletedBook_whenNotExist_shouldThorowException() {
        final Long DELETE_BOOK_ID = -1L;
        bookService.deleteById(DELETE_BOOK_ID);
    }

    @Test
    @Transactional
    public void findAllArticlesForBook_whenBookExistAndContainsArticles_shouldFindSomeArticles() {
        final Long BOOK_ID = 33L;
        Set<Article> articles = bookService.findById(BOOK_ID).get().getArticles();
        assertNotEquals(0, articles.size());
    }

    @Test
    public void findAllArticlesForBook_whenBookDoesNotExist_shouldBeNotPresent() {
        final Long BOOK_ID = -1L;
        Optional<Book> book = bookService.findById(BOOK_ID);
        assertFalse(book.isPresent());
    }


}