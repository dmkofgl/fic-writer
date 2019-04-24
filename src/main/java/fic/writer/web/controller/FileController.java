package fic.writer.web.controller;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.repository.BookRepository;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.FileService;
import fic.writer.domain.service.UserService;
import fic.writer.web.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;
    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/files")
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> takeArticleContentFromFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        return new Resource<>(fileService.parseText(file));
    }

    @PostMapping("/files/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse takeBookFromFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> map = new HashMap<>();
        Book book = fileService.parseBook(file);
        Book createdBook = bookService.create(BookDto.of(book));
        userService.addBookAsAuthor(createdBook.getAuthor().getId(), createdBook.getId());

        createdBook.setArticles(book.getArticles());
        createdBook.getArticles().forEach(a -> {
            a.setBook(createdBook);
            userService.addBookAsAuthor(createdBook.getAuthor().getId(), createdBook.getId());
        });

        return new BookResponse(createdBook);
    }
}
