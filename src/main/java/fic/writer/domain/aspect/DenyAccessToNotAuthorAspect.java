package fic.writer.domain.aspect;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.service.BookService;
import fic.writer.exception.DoesNotHavePermissionException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class DenyAccessToNotAuthorAspect {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuditorAware<Profile> profileAuditorAware;


    @Pointcut("execution(*  fic.writer.domain.service.BookService.deleteById(..)) && args(bookId,..))")
    public void deleteBook(Long bookId) {
    }

    @Pointcut("execution(*  fic.writer.domain.service.BookService.addArticle(..)) && args(bookId,..)) ")
    public void addArticle(Long bookId) {
    }

    @Pointcut("execution(*  fic.writer.domain.service.BookService.removeArticle(..)) && args(bookId,..))")
    public void removedArticle(Long bookId) {
    }

    @Before("deleteBook(bookId) || addArticle(bookId) || removedArticle(bookId)")
    public void denyModifyBookForNoneAuthors(Long bookId) {
        Optional<Profile> optionalProfile = profileAuditorAware.getCurrentAuditor();
        Book book = bookService.findById(bookId).get();

        optionalProfile.ifPresent(p -> {
            if (checkForPermissions(book, p)) {
                throw new DoesNotHavePermissionException();
            }
        });
    }

    private boolean checkForPermissions(Book book, Profile profile) {
        return !(isAuthor(book, profile) || isCoauthor(book, profile));
    }

    private boolean isAuthor(Book book, Profile profile) {
        return book.getAuthor().getId().equals(profile.getId());
    }

    private boolean isCoauthor(Book book, Profile profile) {
        return book.getCoauthors().contains(profile);
    }

}
