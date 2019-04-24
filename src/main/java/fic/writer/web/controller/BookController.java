package fic.writer.web.controller;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.UserService;
import fic.writer.web.response.BookResponse;
import fic.writer.web.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping(value = "/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
    private static final String ID_TEMPLATE_PATH = "/{bookId}";
    private static final String ID_TEMPLATE = "bookId";

    private BookService bookService;
    private UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
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
        Book savedBook = bookService.create(book);
        userService.addBookAsAuthor(savedBook.getAuthor().getId(), savedBook.getId());
        return new BookResponse(savedBook);
    }

    @PutMapping(ID_TEMPLATE_PATH)
    public BookResponse updateBook(@PathVariable(ID_TEMPLATE) Long id, @RequestBody BookDto book) {
        Book savedBook = bookService.update(id, book);
        return new BookResponse(savedBook);
    }

    @DeleteMapping(ID_TEMPLATE_PATH)
    public HttpStatus deleteBook(Long id) {
        bookService.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

    @GetMapping(ID_TEMPLATE_PATH + "/download")
    public ResponseEntity<ByteArrayResource> downloadBook(@PathVariable(ID_TEMPLATE) Long id) throws IOException {
        byte[] bytes = bookService.getBookAsByteArray(id);
        Book book = bookService.findById(id).get();

        ByteArrayResource resource = new ByteArrayResource(bytes);
        MediaType mediaType = MediaType.TEXT_PLAIN;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + book.getTitle() + ".txt")
                .contentType(mediaType)
                .contentLength(bytes.length)
                .body(resource);
    }


}
