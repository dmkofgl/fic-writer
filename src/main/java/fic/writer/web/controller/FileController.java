package fic.writer.web.controller;

import fic.writer.domain.entity.Book;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.FileService;
import fic.writer.domain.service.ProfileService;
import fic.writer.domain.service.WriterService;
import fic.writer.web.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@RestController
public class FileController {
    private static final String BOOK_DOWNLOAD_TEMPLATE_PATH = "/books/{bookId}/download";
    private static final String FILE_CONTENT_PARSE_PATH = "/files/content";
    private static final String PARSE_FILE_AS_BOOK_PATH = "/files/books";

    private static final String BOOK_ID_TEMPLATE = "bookId";
    private static final String FILE_PARAM_NAME = "file";

    private FileService fileService;
    private BookService bookService;

    @Autowired
    public FileController(FileService fileService, BookService bookService, ProfileService profileService, WriterService writerService) {
        this.fileService = fileService;
        this.bookService = bookService;
    }

    @PostMapping(FILE_CONTENT_PARSE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> takeArticleContentFromFile(@RequestParam(FILE_PARAM_NAME) MultipartFile file) {
        return new Resource<>(fileService.parseText(file));
    }

    @PostMapping(PARSE_FILE_AS_BOOK_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse takeBookFromFile(@RequestParam(FILE_PARAM_NAME) MultipartFile file) {
        Book book = fileService.parseBook(file);
        bookService.save(book);
        return new BookResponse(book);
    }

    @GetMapping(BOOK_DOWNLOAD_TEMPLATE_PATH)
    public ResponseEntity<ByteArrayResource> downloadBook(@PathVariable(BOOK_ID_TEMPLATE) Long id) throws IOException {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        final String filenameHeader = "attachment;filename=" + book.getTitle() + ".txt";
        byte[] bookAsByteArray = bookService.getBookAsByteArray(id);
        ByteArrayResource resource = new ByteArrayResource(bookAsByteArray);
        MediaType mediaType = MediaType.TEXT_PLAIN;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, filenameHeader)
                .contentType(mediaType)
                .contentLength(bookAsByteArray.length)
                .body(resource);
    }
}
