package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.auth.OauthProfileDetails;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.web.config.security.authorization.UserPrincipal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WriterServiceTest {
    @Autowired
    private WriterService writerService;
    @Autowired
    private ProfileService profileService;

    @Test(expected = ConstraintViolationException.class)
    public void createBook_whenSecurityContextIsEmpty_shouldThrowException() {
        BookDto bookDto = BookDto.builder().title("bookTitle").build();
        Book book = writerService.saveBook(bookDto);
    }

    @Test
    public void createBook_whenProfileExistInSecurityContext_shouldSaveWithNotNullAuthor() {
        final Long PROFILE_ID = 1L;
        BookDto bookDto = BookDto.builder().title("bookTitle").build();
        setUserInSecurityContext(PROFILE_ID);

        Book book = writerService.saveBook(bookDto);
        Assert.assertNotNull(book.getAuthor());
    }

    @Test
    @Transactional
    public void createBook_whenProfileExistInSecurityContext_shouldAddBookInAuthorCollection() {
        final Long PROFILE_ID = 1L;
        BookDto bookDto = BookDto.builder().build();
        setUserInSecurityContext(PROFILE_ID);

        Book book = writerService.saveBook(bookDto);
        Profile profile = profileService.findById(PROFILE_ID).get();
        Assert.assertTrue(profile.getBooksAsAuthor().contains(book));
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
}