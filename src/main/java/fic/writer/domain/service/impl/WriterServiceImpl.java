package fic.writer.domain.service.impl;

import fic.writer.domain.audit.SpringSecurityAuditorAware;
import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.Profile;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WriterServiceImpl implements WriterService {
    private BookService bookService;
    private ProfileService profileService;
    private SpringSecurityAuditorAware auditorAware;

    @Autowired
    public WriterServiceImpl(BookService bookService, ProfileService profileService, SpringSecurityAuditorAware auditorAware) {
        this.bookService = bookService;
        this.profileService = profileService;
        this.auditorAware = auditorAware;
    }


    @Override
    public Book saveBook(BookDto bookDto) {
        Book book = bookService.create(bookDto);
        Optional<Profile> currentProfile = auditorAware.getCurrentAuditor();
        currentProfile.ifPresent(profile -> {
            Profile updatedProfile = profileService.addBookAsAuthor(profile.getId(), book.getId());
            profileService.save(updatedProfile);
        });

        return book;
    }

    @Override
    public Book saveBook(Book book) {
        Book savedBook = bookService.save(book);
        Optional<Profile> currentProfile = auditorAware.getCurrentAuditor();
        currentProfile.ifPresent(profile -> {
            Profile updatedProfile = profileService.addBookAsAuthor(profile.getId(), savedBook.getId());
            profileService.save(updatedProfile);
        });

        return savedBook;
    }
}
