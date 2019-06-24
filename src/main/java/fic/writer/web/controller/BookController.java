package fic.writer.web.controller;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.service.WriterService;
import fic.writer.web.controller.response.BookResponse;
import fic.writer.web.controller.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private static final String ID_TEMPLATE_PATH = "/{bookId}";
    private static final String ID_TEMPLATE = "bookId";

    private BookService bookService;
    private ProfileService profileService;
    private WriterService writerService;

    @Autowired
    public BookController(BookService bookService, ProfileService profileService, WriterService writerService) {
        this.bookService = bookService;
        this.profileService = profileService;
        this.writerService = writerService;
    }

    @GetMapping
    public PageResponse<BookResponse> getAllBooks(Pageable pageable) {
        Page<BookResponse> resourcePage = bookService.findPage(pageable).map(BookResponse::new);
        return new PageResponse<>(resourcePage);
    }

    @GetMapping(ID_TEMPLATE_PATH)
    public BookResponse getBookById(@PathVariable(ID_TEMPLATE) Long id) {
        return bookService.findById(id)
                .map(BookResponse::new)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@RequestBody BookDto book) {
        Book savedBook = writerService.saveBook(book);
        return new BookResponse(savedBook);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public BookResponse updateBook(@PathVariable(ID_TEMPLATE) Long id, @RequestBody BookDto book) {
        Book savedBook = bookService.update(id, book);
        return new BookResponse(savedBook);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable(ID_TEMPLATE) Long id) {
        bookService.deleteById(id);
    }


}
