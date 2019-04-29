package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.web.config.security.authorization.EmbeddedProfileDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WriterServiceTest {
    @Autowired
    private WriterService writerService;
    @Autowired
    private ProfileService profileService;

    @Test
    public void createBook_whenSecurityContextIsEmpty_shouldSaveWithoutAuthor() {
        BookDto bookDto = BookDto.builder().title("bookTitle").build();
        Book book = writerService.createBook(bookDto);
        Assert.assertNull(book.getAuthor());
    }

    @Test
    public void createBook_whenProfileExistInSecurityContext_shouldSaveWithNotNullAuthor() {
        BookDto bookDto = BookDto.builder().title("bookTitle").build();
        setUserInSecurityContext();

        Book book = writerService.createBook(bookDto);
        Assert.assertNotNull(book.getAuthor());
    }

    @Test
    @Transactional
    public void createBook_whenProfileExistInSecurityContext_shouldAddBookInAuthorCollection() {
        final Long PROFILE_ID = 1L;
        BookDto bookDto = BookDto.builder().build();
        Profile profile = Profile.builder().id(PROFILE_ID).build();
        setUserInSecurityContext(profile);

        Book book = writerService.createBook(bookDto);
        profile = profileService.findById(PROFILE_ID).get();
        Assert.assertTrue(profile.getBooksAsAuthor().contains(book));
    }

    private void setUserInSecurityContext() {
        Profile profile = Profile.builder().id(1L).build();
        this.setUserInSecurityContext(profile);
    }

    private void setUserInSecurityContext(Profile profile) {
        final String PASSWORD = "qwerty";
        EmbeddedProfileDetails embeddedProfileDetails = new EmbeddedProfileDetails(profile, PASSWORD);
        TestingAuthenticationToken token = new TestingAuthenticationToken(embeddedProfileDetails, null);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}