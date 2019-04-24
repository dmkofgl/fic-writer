package fic.writer.domain.service;

import fic.writer.domain.entity.Book;
import fic.writer.domain.entity.dto.ArticleDto;
import fic.writer.domain.entity.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Page<Book> findPage(Pageable pageable);

    Optional<Book> findById(Long aLong);

    Book create(BookDto bookDto);

    Book update(Long id, BookDto bookDto);

    Book addArticle(Long bookId, ArticleDto articleDto);

    Book removeArticle(Long bookId, Long articleId);

    void delete(Book book);

    void deleteById(Long aLong);

    byte[] getBookAsByteArray(Long bookId) throws IOException;
}
