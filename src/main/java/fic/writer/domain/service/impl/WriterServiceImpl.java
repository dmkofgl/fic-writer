package fic.writer.domain.service.impl;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.service.WriterService;
import fic.writer.web.config.security.authorization.EmbeddedProfileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WriterServiceImpl implements WriterService {
    private BookService bookService;
    private ProfileService profileService;

    @Autowired
    public WriterServiceImpl(BookService bookService, ProfileService profileService) {
        this.bookService = bookService;
        this.profileService = profileService;
    }

    @Override
    public Book createBook(BookDto bookDto) {
        Book book = bookService.create(bookDto);
        Optional<Profile> currentProfile = getCurrentProfile();
        currentProfile.ifPresent(profile -> {
            Profile updatedProfile = profileService.addBookAsAuthor(profile.getId(), book.getId());
            profileService.save(updatedProfile);
        });

        return book;
    }

    @Override
    public void deleteBook(Long bookId) {
        Optional<Profile> currentProfile = getCurrentProfile();
        currentProfile.ifPresent(profile -> {
            profile.getBooksAsAuthor().removeIf(book -> bookId.equals(book.getId()));
            profile.getBooksAsCoauthor().removeIf(book -> bookId.equals(book.getId()));
            profileService.save(profile);
        });
    }

    private Optional<Profile> getCurrentProfile() {
        Optional<Profile> user = Optional.empty();
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            user = Optional.ofNullable(
                    ((EmbeddedProfileDetails)
                            SecurityContextHolder
                                    .getContext()
                                    .getAuthentication()
                                    .getPrincipal()
                    ).getProfile());
        }
        return user;
    }
}
