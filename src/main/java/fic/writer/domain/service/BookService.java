package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.entity.dto.BookDto;
import fic.writer.domain.entity.enums.FileExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Page<Book> findPage(Pageable pageable);

    Optional<Book> findById(Long bookId);

    Book create(BookDto bookDto);

    Book save(Book book);

    Book update(Long id, BookDto bookDto);

    Book addArticle(Long bookId, ArticleDto articleDto);

    Book removeArticle(Long bookId, Long articleId);

    void delete(Book book);

    void deleteById(Long bookId);

    byte[] convertBookToByteArray(Book book, FileExtension fileExtension);
}
