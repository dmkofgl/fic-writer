package fic.writer.web.controller;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.enums.FileExtension;
import fic.writer.domain.service.BookService;
import fic.writer.domain.service.FileService;
import fic.writer.domain.service.WriterService;
import fic.writer.web.controller.response.BookResponse;
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
    private static final String BOOK_DOWNLOAD_TEMPLATE_PATH = "/api/books/{bookId}/download";
    private static final String FILE_CONTENT_PARSE_PATH = "/api/files/content";
    private static final String PARSE_FILE_AS_BOOK_PATH = "/api/files/books";

    private static final String BOOK_ID_TEMPLATE = "bookId";
    private static final String FILE_NAME_PARAMETER = "file";

    private FileService fileService;
    private BookService bookService;
    private WriterService writerService;

    @Autowired
    public FileController(FileService fileService, BookService bookService, WriterService writerService) {
        this.fileService = fileService;
        this.bookService = bookService;
        this.writerService = writerService;
    }

    @PostMapping(FILE_CONTENT_PARSE_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public Resource<String> takeArticleContentFromFile(@RequestParam(FILE_NAME_PARAMETER) MultipartFile file) {
        return new Resource<>(fileService.parseText(file));
    }

    @PostMapping(PARSE_FILE_AS_BOOK_PATH)
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse parseBookFromFile(@RequestParam(FILE_NAME_PARAMETER) MultipartFile file) {
        Book book = fileService.parseBook(file);
        writerService.saveBook(book);
        return new BookResponse(book);
    }

    @GetMapping(BOOK_DOWNLOAD_TEMPLATE_PATH)
    public ResponseEntity<ByteArrayResource> getBookAsByteArray(@PathVariable(BOOK_ID_TEMPLATE) Long id, FileExtension fileExtension) throws IOException {
        Book book = bookService.findById(id).orElseThrow(EntityNotFoundException::new);
        final String filenameHeader = getAttachmentHeader(book.getTitle(), fileExtension);
        byte[] bookAsByteArray = bookService.convertBookToByteArray(book);
        ByteArrayResource resource = new ByteArrayResource(bookAsByteArray);
        MediaType mediaType = MediaType.TEXT_PLAIN;
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, filenameHeader)
                .contentType(mediaType)
                .contentLength(bookAsByteArray.length)
                .body(resource);
    }

    private String getAttachmentHeader(String title, FileExtension fileExtension) {
        String extensionNameInLowerCase = fileExtension.name().toLowerCase();
        return "attachment;filename=" + title + "." + extensionNameInLowerCase;
    }
}
